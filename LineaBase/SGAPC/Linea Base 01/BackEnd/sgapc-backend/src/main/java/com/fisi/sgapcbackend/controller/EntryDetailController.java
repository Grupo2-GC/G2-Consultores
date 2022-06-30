package com.fisi.sgapcbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fisi.sgapcbackend.entities.EntryDetail;
import com.fisi.sgapcbackend.services.IEntryDetailService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/entry-detail")
public class EntryDetailController {
    @Autowired
    private IEntryDetailService entryDetailService;

    @GetMapping(path = "/gled")
    public ResponseEntity<?> index(){
        Map<String, Object> response = new HashMap<>();
        try{
            List<EntryDetail> l = entryDetailService.getAll();
            response.put("data",l);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error", "Error al realizar la consulta de la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured({"ROLE_GROCER","ROLE_ADMIN"})
    @GetMapping(value = "/shed/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        EntryDetail entryDetail = null;
        try{
            entryDetail = entryDetailService.findById(id);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(entryDetail == null){
            response.put("message",
                    "El detalle de la entrada con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", entryDetail);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @Secured({"ROLE_GROCER","ROLE_ADMIN"})
    @PostMapping(path = "/ced")
    public ResponseEntity<?> create(@Valid @RequestBody EntryDetail entryDetail, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        EntryDetail ed = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            ed = entryDetailService.save(entryDetail);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El detalle de la entrada ha sido creado con exito");
        response.put("data", ed);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_GROCER","ROLE_ADMIN"})
    @PutMapping(value = "/ued/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody EntryDetail entryDetail, BindingResult result,
                                    @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        EntryDetail current = entryDetailService.findById(id);
        EntryDetail updateEntryDetail = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (current == null) {
            response.put("message", "Error no se pudo editar el detalle de la entrada con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            current.setProduct(entryDetail.getProduct());
            current.setQuantity_entry(entryDetail.getQuantity_entry());
            current.setPurchase_price(entryDetail.getPurchase_price());
            updateEntryDetail = entryDetailService.save(current);
            response.put("message", "El detalle de la entrada ha sido actualizada con exito!");
            response.put("data", updateEntryDetail);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured({"ROLE_GROCER","ROLE_ADMIN"})
    @DeleteMapping(value = "/ded/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            EntryDetail ed = entryDetailService.findById(id);
            entryDetailService.deleteById(id);
            response.put("message", "La entrada ha sido eliminada con exito!");
            response.put("data", ed);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar la entrada de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
