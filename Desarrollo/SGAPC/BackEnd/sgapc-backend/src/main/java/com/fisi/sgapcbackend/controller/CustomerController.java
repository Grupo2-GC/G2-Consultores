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

import com.fisi.sgapcbackend.entities.Customer;
import com.fisi.sgapcbackend.services.ICustomerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping(path = "/glcu")
    public ResponseEntity<?> index(){
        Map<String, Object> response = new HashMap<>();

        try {
            List<Customer> l = customerService.getAll();
            response.put("data", l);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error", "Error al realizar la consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/glcu/page/{page}")
	public ResponseEntity<?> index(@PathVariable Integer page) {
		Map<String, Object> response = new HashMap<>();
		try {
			Pageable pageable = PageRequest.of(page, 4);
			Page<Customer> p = customerService.getAll(pageable);
			response.put("data", p);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("error", "Error al realizar la consulta select a la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
    
    @Secured({"ROLE_ADMIN"})
    @GetMapping(value = "/shcu/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Customer cu = null;
        try{
            cu = customerService.findById(id);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(cu == null){
            response.put("message",
                    "La categoria con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", cu);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    
    @Secured({"ROLE_ADMIN"})
    @PostMapping(path = "/ctcu")
    public ResponseEntity<?> create(@Valid  @RequestBody Customer customer, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        Customer cu = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            cu = customerService.save(customer);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El cliente ha sido creado con exito");
        response.put("data", cu);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @Secured({"ROLE_ADMIN"})
    @PutMapping(value = "/ucu/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Customer customer, BindingResult result,
                                    @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Customer current = customerService.findById(id);
        Customer updateCustomer = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (current == null) {
            response.put("message", "Error no se pudo editar la categoria con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            current.setFirstname(customer.getFirstname());
            current.setLastname(customer.getLastname());
            current.setTelephone(customer.getTelephone());
            current.setAddress(customer.getAddress());
            current.setRuc(customer.getRuc());
            current.setEmail(customer.getEmail());
            current.setCreateAt(customer.getCreateAt());
            current.setUpdateAt(customer.getUpdateAt());
            updateCustomer = customerService.save(current);
            response.put("message", "El cliente ha sido actualizado con exito!");
            response.put("data", updateCustomer);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(value = "/dcu/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            Customer cu = customerService.findById(id);
            customerService.deleteById(id);
            response.put("message", "El cliente ha sido eliminado con exito!");
            response.put("data", cu);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar el cliente de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Secured({"ROLE_ADMIN"})
    @GetMapping(value = "/fcbf/{fterm}/{lterm}")
    public ResponseEntity<?> filterCustomerByFullname(@PathVariable String fterm, @PathVariable String lterm){
    	Map<String, Object> response = new HashMap<>();

        try {
            List<Customer> l = customerService.findCustomerByFullname(fterm, lterm);
            response.put("data", l);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error", "Error al realizar la consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    	
    }
    
    @Secured({"ROLE_ADMIN"})
    @GetMapping(value = "/fcbd/{dterm}")
    public ResponseEntity<?> filterCustomerByDni(@PathVariable String dterm){
    	Map<String, Object> response = new HashMap<>();

        try {
            List<Customer> l = customerService.findCustomerByDni(dterm);
            response.put("data", l);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error", "Error al realizar la consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    	
    }

}
