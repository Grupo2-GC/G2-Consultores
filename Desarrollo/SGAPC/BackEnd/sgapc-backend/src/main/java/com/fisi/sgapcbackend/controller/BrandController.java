package com.fisi.sgapcbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fisi.sgapcbackend.entities.Brand;
import com.fisi.sgapcbackend.services.IBrandService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*","http://localhost:4200"})
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private IBrandService brandService;

    @Secured({"ROLE_ADMIN"})
    @GetMapping(path = "/glb")
    public ResponseEntity<?> index(){
        Map<String, Object> response = new HashMap<>();
        try{
            List<Brand> l = brandService.getAll();
            response.put("data",l);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error", "Error al realizar la consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Secured({"ROLE_ADMIN"})
    @GetMapping(value = "/shb/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Brand b = null;
        try {
            b = brandService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (b == null) {
            response.put("message",
                    "La marca con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", b);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    
    @Secured({"ROLE_ADMIN"})
    @PostMapping(path = "/ctb")
    public ResponseEntity<?> create(@Valid @RequestBody Brand brand, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        Brand b = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            b = brandService.save(brand);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "La marca ha sido creada con exito!");
        response.put("data",b);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @Secured({"ROLE_ADMIN"})
    @PutMapping(value = "/upb/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Brand brand, BindingResult result,
                                    @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Brand current = brandService.findById(id);
        Brand updateBrand = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (current == null) {
            response.put("message", "Error no se pudo editar, la marca con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            current.setName(brand.getName());
            current.setCountry(brand.getCountry());
            current.setUpdateAt(brand.getUpdateAt());
            current.setCreateAt(brand.getCreateAt());
            updateBrand = brandService.save(current);
            response.put("message", "La marca ha sido actualizada con exito!");
            response.put("data", updateBrand);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(value = "/deb/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Brand b = brandService.findById(id);
            brandService.deleteById(id);
            response.put("message", "La marca ha sido eliminada con exito!");
            response.put("data", b);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar la marca de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
