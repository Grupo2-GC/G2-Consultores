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

import com.fisi.sgapcbackend.entities.WithdrawalDetail;
import com.fisi.sgapcbackend.services.IWithdrawalDetailService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/withdrawal-detail")
public class WithdrawalDetailController {

    @Autowired
    private IWithdrawalDetailService withdrawalDetailService;

    @GetMapping(path = "/glwd")
    public ResponseEntity<?> index(){
        Map<String, Object> response = new HashMap<>();
        try{
            List<WithdrawalDetail> l = withdrawalDetailService.getAll();
            response.put("data",l);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error", "Error al realizar la consulta de la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Secured({"ROLE_GROCER"})
    @GetMapping(value = "/shwd/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        WithdrawalDetail wtd = null;
        try{
        	wtd = withdrawalDetailService.findById(id);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(wtd == null){
            response.put("message",
                    "El detalle de la salida del almacen con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", wtd);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @Secured({"ROLE_GROCER"})
    @PostMapping(path = "/ctwd")
    public ResponseEntity<?> create(@Valid @RequestBody WithdrawalDetail withdrawalDetail, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        WithdrawalDetail wtd = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            wtd = withdrawalDetailService.save(withdrawalDetail);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El detalle de la salida del inventario ha sido creada con exito");
        response.put("data",wtd);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @Secured({"ROLE_GROCER"})
    @PutMapping(value = "/udwd/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody WithdrawalDetail withdrawalDetail, BindingResult result,
                                    @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        WithdrawalDetail current = withdrawalDetailService.findById(id);
        WithdrawalDetail updateWithdrawalDetail = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (current == null) {
            response.put("message", "Error no se pudo editar el detalle de la salida del inventario con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            current.setAmount(withdrawalDetail.getAmount());
            current.setProduct(withdrawalDetail.getProduct());
            current.setQuantity_removal(withdrawalDetail.getQuantity_removal());
            updateWithdrawalDetail = withdrawalDetailService.save(current);
            response.put("message", "El detalle de la salida del inventario ha sido actualizada con exito!");
            response.put("data", updateWithdrawalDetail);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured({"ROLE_GROCER"})
    @DeleteMapping(value = "/dwt/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            WithdrawalDetail wtd = withdrawalDetailService.findById(id);
            withdrawalDetailService.deleteById(id);
            response.put("message", "El detalle de la salida del inventario ha sido eliminada con exito!");
            response.put("data", wtd);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar la  de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
