package com.fisi.sgapcbackend.controller;

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

import com.fisi.sgapcbackend.dto.ProductDTO;
import com.fisi.sgapcbackend.response.ProductResponse;
import com.fisi.sgapcbackend.services.IProductService;
import com.fisi.sgapcbackend.utils.Constants;

@CrossOrigin(origins = {"*", "http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	@Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @GetMapping(path="/products")
    public ResponseEntity<?> getAll(
    		@RequestParam(value = "pageNo", defaultValue = Constants.NUMBER_OF_PAGE_PEER_DEFAULT, required = false) int numberOfPage,
			@RequestParam(value = "pageSize", defaultValue = Constants.SIZE_OF_PAGE_PEER_DEFAULT, required = false) int sizeOfPage,
			@RequestParam(value = "sortBy", defaultValue = Constants.SORT_PEER_DEFAULT, required = false) String sortPeer,
			@RequestParam(value = "sortDir", defaultValue = Constants.SORT_DIRECTION_PEER_DEFAULT, required = false) String sortDir){
    	
    	Map<String, Object> response = new HashMap<>();
    	
        try{
        	ProductResponse productResponse = productService.getAll(numberOfPage, sizeOfPage, sortPeer, sortDir);
            response.put("data",productResponse);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

        }catch (Exception e){
            response.put("error", "Error al realizar la consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    	
    }
	
	@Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @GetMapping(path = "/product")
    public ResponseEntity<?> show(@RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();
        ProductDTO p = null;
        try {
            p = productService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("data", p);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
	
	@Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @PostMapping(path = "/product")
    public ResponseEntity<?> create(@Valid @RequestBody ProductDTO productDTO, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        ProductDTO p = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            p = productService.save(productDTO);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El producto ha sido creado con exito!");
        response.put("data",p);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
	
	@Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @PutMapping(path = "/product")
    public ResponseEntity<?> update(@Valid @RequestBody ProductDTO productDTO, BindingResult result,
    		@RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();
        ProductDTO responseProduct = productService.update(productDTO, id);

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            response.put("message", "El producto ha sido actualizado con exito!");
            response.put("data", responseProduct);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @DeleteMapping(path = "/product")
    public ResponseEntity<?> delete(@RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            ProductDTO p = productService.findById(id);
            productService.deleteById(id);
            response.put("message", "El producto ha sido eliminado con exito!");
            response.put("data", p);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar el producto de la base de datos de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	

}
