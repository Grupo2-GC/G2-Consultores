package com.fisi.sgapcbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fisi.sgapcbackend.dto.UserDTO;
import com.fisi.sgapcbackend.response.UserResponse;
import com.fisi.sgapcbackend.services.IUserService;
import com.fisi.sgapcbackend.utils.Constants;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*", "http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUserService userService;
    
	@Secured({"ROLE_ADMIN"})
    @GetMapping(path="/users")
    public ResponseEntity<?> getAll(
    		@RequestParam(value = "pageNo", defaultValue = Constants.NUMBER_OF_PAGE_PEER_DEFAULT, required = false) int numberOfPage,
			@RequestParam(value = "pageSize", defaultValue = Constants.SIZE_OF_PAGE_PEER_DEFAULT, required = false) int sizeOfPage,
			@RequestParam(value = "sortBy", defaultValue = Constants.SORT_PEER_DEFAULT, required = false) String sortPeer,
			@RequestParam(value = "sortDir", defaultValue = Constants.SORT_DIRECTION_PEER_DEFAULT, required = false) String sortDir){
    	
    	Map<String, Object> response = new HashMap<>();
    	
        try{
        	UserResponse userResponse = userService.getAll(numberOfPage, sizeOfPage, sortPeer, sortDir);
            response.put("data",userResponse);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

        }catch (Exception e){
            response.put("error", "Error al realizar la consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    	
    }
    
	@Secured({"ROLE_ADMIN"})
    @GetMapping(value = "/user")
    public ResponseEntity<?> show(@RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();
        UserDTO u = null;
        try{
            u = userService.findById(id);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(u == null){
            response.put("message",
                    "El usuario con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", u);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

	@Secured({"ROLE_ADMIN"})
    @PostMapping(path = "/user")
    public ResponseEntity<?> create(@Valid @RequestBody UserDTO userDTO, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        UserDTO u = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            u = userService.save(userDTO);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El usuario ha sido creado con exito");
        response.put("data", u);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @PatchMapping(path = "/user")
    public ResponseEntity<?> addRoleToUser(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId){
        Map<String, Object> response = new HashMap<>();
        UserDTO userDTO = null;
        try {
            userDTO = userService.addRoleToUser(userId, roleId);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El rol ha sido asignado con exito");
        response.put("data", userDTO);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

	@Secured({"ROLE_ADMIN"})
    @PutMapping(path = "/user")
    public ResponseEntity<?> update(@Valid @RequestBody UserDTO userDTO, BindingResult result,
    		@RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();
        
        UserDTO responseUser = userService.update(userDTO, id);

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (responseUser == null) {
            response.put("message", "Error no se pudo editar el usuario con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            response.put("message", "El usuario ha sido actualizado con exito!");
            response.put("data", responseUser);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
    }

	@Secured({"ROLE_ADMIN"})
    @DeleteMapping(path = "/user")
    public ResponseEntity<?> delete(@RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            UserDTO u = userService.findById(id);
            userService.deleteById(id);
            response.put("message", "El usuario ha sido eliminado con exito!");
            response.put("data", u);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar el usuario de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
