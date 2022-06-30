package com.fisi.sgapcbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fisi.sgapcbackend.entities.Withdrawal;
import com.fisi.sgapcbackend.services.IWithdrawalService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/withdrawal")
public class WithdrawalController {

    @Autowired
    private IWithdrawalService withdrawalService;

    @GetMapping(path = "/glw")
    public ResponseEntity<?> index(){
        Map<String, Object> response = new HashMap<>();
        try{
            List<Withdrawal> l = withdrawalService.getAll();
            response.put("data",l);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error", "Error al realizar la consulta de la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Secured({"ROLE_GROCER","ROLE_ADMIN"})
    @GetMapping(value = "/shw/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Withdrawal wt = null;
        try{
            wt = withdrawalService.findById(id);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(wt == null){
            response.put("message",
                    "La salida del almacen con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", wt);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @Secured({"ROLE_GROCER","ROLE_ADMIN"})
    @PostMapping(path = "/ctw")
    public ResponseEntity<?> create(@Valid @RequestBody Withdrawal withdrawal, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        Withdrawal wt = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            wt = withdrawalService.save(withdrawal);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "La salida del inventario ha sido creada con exito");
        response.put("data",wt);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @Secured({"ROLE_GROCER","ROLE_ADMIN"})
    @PutMapping(value = "/udw/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Withdrawal withdrawal, BindingResult result,
                                    @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Withdrawal current = withdrawalService.findById(id);
        Withdrawal updateWithdrawal = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (current == null) {
            response.put("message", "Error no se pudo editar la salida del inventario con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            current.setCustomer(withdrawal.getCustomer());
            current.setDate_withdrawal(withdrawal.getDate_withdrawal());
            current.setWithdrawalDetails(withdrawal.getWithdrawalDetails());
            current.setUser(withdrawal.getUser());
            current.setIgv(withdrawal.getIgv());
            current.setTotal(withdrawal.getTotal());
            updateWithdrawal = withdrawalService.save(current);
            response.put("message", "La salida del inventario ha sido actualizada con exito!");
            response.put("data", updateWithdrawal);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured({"ROLE_GROCER","ROLE_ADMIN"})
    @DeleteMapping(value = "/dwt/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            Withdrawal wt = withdrawalService.findById(id);
            withdrawalService.deleteById(id);
            response.put("message", "La salida del inventario ha sido eliminada con exito!");
            response.put("data", wt);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar la  de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
