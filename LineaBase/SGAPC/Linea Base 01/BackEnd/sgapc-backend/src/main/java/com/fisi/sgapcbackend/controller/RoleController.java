package com.fisi.sgapcbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fisi.sgapcbackend.entities.Role;
import com.fisi.sgapcbackend.services.IRoleService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;
    
    
    @GetMapping(path = "/glr")
    public ResponseEntity<?> index(){
        Map<String, Object> response = new HashMap<>();
        try{
            List<Role> l = roleService.getAll();
            response.put("data",l);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error", "Error al realizar la consulta de la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping(value = "/shr/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Role role = null;
        try{
            role = roleService.findById(id);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(role == null){
            response.put("message",
                    "El Rol con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", role);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @Secured({"ROLE_ADMIN"})
    @PostMapping(path = "/ctr")
    public ResponseEntity<?> create(@Valid @RequestBody Role role, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        Role ro = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            ro = roleService.save(role);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El rol ha sido creado con exito");
        response.put("data", ro);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @Secured({"ROLE_ADMIN"})
    @PutMapping(value = "/upr/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Role role, BindingResult result,
                                    @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Role current = roleService.findById(id);
        Role updateRole = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (current == null) {
            response.put("message", "Error no se pudo editar el rol con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            current.setName(role.getName());
            updateRole = roleService.save(current);
            response.put("message", "El rol ha sido actualizado con exito!");
            response.put("data", updateRole);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(value = "/dr/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            Role role = roleService.findById(id);
            roleService.deleteById(id);
            response.put("message", "El rol ha sido eliminado con exito!");
            response.put("data", role);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar el rol de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
