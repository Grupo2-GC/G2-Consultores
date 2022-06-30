package com.fisi.sgapcbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fisi.sgapcbackend.entities.User;
import com.fisi.sgapcbackend.services.IUserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(path = "/glu")
    public ResponseEntity<?> index(){
        Map<String, Object> response = new HashMap<>();

        try{
            List<User> l = userService.getAll();
            response.put("data",l);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

        }catch (Exception e){
            response.put("error", "Error al realizar la consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Secured({"ROLE_ADMIN"})
    @GetMapping(value = "/glu/page/{page}")
	public ResponseEntity<?> index(@PathVariable Integer page) {
		Map<String, Object> response = new HashMap<>();
		try {
			Pageable pageable = PageRequest.of(page, 4);
			Page<User> p = userService.getAll(pageable);
			response.put("data", p);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("error", "Error al realizar la consulta select a la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
    @Secured({"ROLE_ADMIN"})
    @GetMapping(value = "/shu/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        User u = null;
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

    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/ctu")
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        User u = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            u = userService.save(user);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El usuario ha sido creado con exito");
        response.put("data", u);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/upu/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result,
                                    @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        User current = userService.findById(id);
        User updateUser = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (current == null) {
            response.put("message", "Error no se pudo editar el usuario con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            current.setFirstname(user.getFirstname());
            current.setLastname(user.getLastname());
            current.setTelephone(user.getTelephone());
            current.setAddress(user.getAddress());
            current.setAge(user.getAge());
            current.setDni(user.getDni());
            current.setEmail(user.getEmail());
            current.setRoles(user.getRoles());
            current.setPassword(user.getPassword());
            current.setCreateAt(user.getCreateAt());
            current.setUpdateAt(user.getUpdateAt());
            updateUser = userService.save(current);
            response.put("message", "El usuario ha sido actualizado con exito!");
            response.put("data", updateUser);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/du/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            User u = userService.findById(id);
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
