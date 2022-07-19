package com.fisi.sgapcbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fisi.sgapcbackend.dto.CategoryDTO;
import com.fisi.sgapcbackend.response.CategoryResponse;
import com.fisi.sgapcbackend.services.ICategoryService;
import com.fisi.sgapcbackend.utils.Constants;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*", "http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @GetMapping(path="/categories")
    public ResponseEntity<?> getAll(
    		@RequestParam(value = "pageNo", defaultValue = Constants.NUMBER_OF_PAGE_PEER_DEFAULT, required = false) int numberOfPage,
			@RequestParam(value = "pageSize", defaultValue = Constants.SIZE_OF_PAGE_PEER_DEFAULT, required = false) int sizeOfPage,
			@RequestParam(value = "sortBy", defaultValue = Constants.SORT_PEER_DEFAULT, required = false) String sortPeer,
			@RequestParam(value = "sortDir", defaultValue = Constants.SORT_DIRECTION_PEER_DEFAULT, required = false) String sortDir,
			@RequestParam(value = "name", required = false) String nameOfCategory){
    	
    	Map<String, Object> response = new HashMap<>();
    	
        try{
        	
        	CategoryResponse categoryResponse = categoryService.getAll(numberOfPage, sizeOfPage, sortPeer, sortDir);
            response.put("data",categoryResponse);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

        }catch (Exception e){
            response.put("error", "Error al realizar la consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    	
    }

    @Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @GetMapping(path = "/category")
    public ResponseEntity<?> show(
    		@RequestParam(value="id", required=false) Long id,
    		@RequestParam(value = "name", required = false) String nameOfCategory){
        
    	Map<String, Object> response = new HashMap<>();
    	
        //CategoryDTO c = null;
        
        List<CategoryDTO> cr= new ArrayList<CategoryDTO>();
        
        try {
        	
        	if(id!=null && nameOfCategory==null || id!=null) {
                CategoryDTO c = categoryService.findById(id);
                cr.add(c);
                //response.put("data", cr);
        	}
        	if(nameOfCategory!=null && id==null) {
        		cr = categoryService.findCategoryByName(nameOfCategory);
                //response.put("data", cr);
        	}
        	
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (id == null && nameOfCategory  == null) {
            response.put("message","No ingreso los datos necesarios para filtrar las marcas(id o nombre de la marca)");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", cr);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    
    @Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @PostMapping(path = "/category")
    public ResponseEntity<?> create(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        CategoryDTO c = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            c = categoryService.save(categoryDTO);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "La categoria ha sido creada con exito!");
        response.put("data",c);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @PutMapping(path = "/category")
    public ResponseEntity<?> update(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result,
    		@RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();
		CategoryDTO responseCategory = categoryService.update(categoryDTO, id);

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (responseCategory == null) {
            response.put("message", "Error no se pudo editar la categoria con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            response.put("message", "La categoria ha sido actualizada con exito!");
            response.put("data", responseCategory);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @DeleteMapping(path = "/category")
    public ResponseEntity<?> delete(@RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            CategoryDTO c = categoryService.findById(id);
            categoryService.deleteById(id);
            response.put("message", "La categoria ha sido eliminada con exito!");
            response.put("data", c);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar la categoria de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
