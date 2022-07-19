package com.fisi.sgapcbackend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fisi.sgapcbackend.dto.CustomerDTO;
import com.fisi.sgapcbackend.response.CustomerResponse;
import com.fisi.sgapcbackend.services.ICustomerService;
import com.fisi.sgapcbackend.utils.Constants;

@CrossOrigin(origins = {"*", "http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	private ICustomerService customerService;
	
	@Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @GetMapping(path="/customers")
    public ResponseEntity<?> getAll(
    		@RequestParam(value = "pageNo", defaultValue = Constants.NUMBER_OF_PAGE_PEER_DEFAULT, required = false) int numberOfPage,
			@RequestParam(value = "pageSize", defaultValue = Constants.SIZE_OF_PAGE_PEER_DEFAULT, required = false) int sizeOfPage,
			@RequestParam(value = "sortBy", defaultValue = Constants.SORT_PEER_DEFAULT, required = false) String sortPeer,
			@RequestParam(value = "sortDir", defaultValue = Constants.SORT_DIRECTION_PEER_DEFAULT, required = false) String sortDir){
    	
    	Map<String, Object> response = new HashMap<>();
    	
        try{
        	CustomerResponse customerResponse = customerService.getAll(numberOfPage, sizeOfPage, sortPeer, sortDir);
            response.put("data",customerResponse);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

        }catch (Exception e){
            response.put("error", "Error al realizar la consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    	
    }
	
	@Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @GetMapping(path = "/customer")
    public ResponseEntity<?> show(
    		@RequestParam(value="id", required=false) Long id,
    		@RequestParam(value = "dni", required = false) String dni){
        Map<String, Object> response = new HashMap<>();
        List<CustomerDTO> cr= new ArrayList<CustomerDTO>();
        try {
        	if(id!=null && dni==null || id!=null) {
        		CustomerDTO c = customerService.findById(id);
        		cr.add(c);

        	}
        	if(dni!=null && id==null) {
        		cr = customerService.findCustomerByDni(dni);
        	}
            
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (id == null && dni  == null) {
            response.put("message","No ingreso los datos necesarios para filtrar las marcas(id o o dni del cliente)");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", cr);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
	
	
	@Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @PostMapping(path = "/customer")
    public ResponseEntity<?> create(@Valid @RequestBody CustomerDTO customerDTO, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        CustomerDTO c = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            c = customerService.save(customerDTO);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El cliente ha sido creado con exito!");
        response.put("data",c);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
	
	
	@Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @PutMapping(path = "/customer")
    public ResponseEntity<?> update(@Valid @RequestBody CustomerDTO customerDTO, BindingResult result,
    		@RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();
        CustomerDTO responseCustomer = customerService.update(customerDTO, id);

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            response.put("message", "El cliente ha sido actualizado con exito!");
            response.put("data", responseCustomer);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @DeleteMapping(path = "/customer")
    public ResponseEntity<?> delete(@RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            CustomerDTO c = customerService.findById(id);
            customerService.deleteById(id);
            response.put("message", "El cliente ha sido eliminado con exito!");
            response.put("data", c);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar al cliente de la base de datos de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
