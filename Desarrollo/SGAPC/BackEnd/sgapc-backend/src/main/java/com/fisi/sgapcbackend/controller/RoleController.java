package com.fisi.sgapcbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import com.fisi.sgapcbackend.dto.RoleDTO;
import com.fisi.sgapcbackend.response.RoleResponse;
import com.fisi.sgapcbackend.services.IRoleService;
import com.fisi.sgapcbackend.utils.Constants;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*", "http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private IRoleService roleService;
    
    @Secured({"ROLE_ADMIN"})
    @GetMapping(path="/roles")
    public ResponseEntity<?> getAll(
    		@RequestParam(value = "pageNo", defaultValue = Constants.NUMBER_OF_PAGE_PEER_DEFAULT, required = false) int numberOfPage,
			@RequestParam(value = "pageSize", defaultValue = Constants.SIZE_OF_PAGE_PEER_DEFAULT, required = false) int sizeOfPage,
			@RequestParam(value = "sortBy", defaultValue = Constants.SORT_PEER_DEFAULT, required = false) String sortPeer,
			@RequestParam(value = "sortDir", defaultValue = Constants.SORT_DIRECTION_PEER_DEFAULT, required = false) String sortDir){
    	
    	Map<String, Object> response = new HashMap<>();
    	
        try{
        	RoleResponse roleResponse = roleService.getAll(numberOfPage, sizeOfPage, sortPeer, sortDir);
            response.put("data",roleResponse);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

        }catch (Exception e){
            response.put("error", "Error al realizar la consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    	
    }
    @Secured({"ROLE_ADMIN"})
    @GetMapping(path = "/role")
    public ResponseEntity<?> show(@RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();
        RoleDTO r = null;
        try{
            r = roleService.findById(id);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(r == null){
            response.put("message",
                    "El Rol con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", r);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @Secured({"ROLE_ADMIN"})
    @PostMapping(path = "/role")
    public ResponseEntity<?> create(@Valid @RequestBody RoleDTO roleDTO, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        RoleDTO ro = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            ro = roleService.save(roleDTO);
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
    @PutMapping(value = "/role")
    public ResponseEntity<?> update(@Valid @RequestBody RoleDTO roleDTO, BindingResult result,
    		@RequestParam("id") Long id){
    	Map<String, Object> response = new HashMap<>();
		RoleDTO responseRole = roleService.update(roleDTO, id);

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (responseRole == null) {
            response.put("message", "Error no se pudo editar la categoria con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            response.put("message", "La categoria ha sido actualizada con exito!");
            response.put("data", responseRole);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(path = "/role")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            RoleDTO r = roleService.findById(id);
            roleService.deleteById(id);
            response.put("message", "El rol ha sido eliminado con exito!");
            response.put("data", r);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar el rol de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
