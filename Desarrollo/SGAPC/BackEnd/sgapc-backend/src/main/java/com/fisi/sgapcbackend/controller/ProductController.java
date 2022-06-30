package com.fisi.sgapcbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fisi.sgapcbackend.entities.Product;
import com.fisi.sgapcbackend.services.IProductService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping(path = "/glp")
    public ResponseEntity<?> index(){
        Map<String, Object> response = new HashMap<>();
        try{
            List<Product> l = productService.getAll();
            response.put("data",l);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error", "Error al realizar la consulta de la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Secured({"ROLE_GROCER","ROLE_ADMIN"})
    @GetMapping(value = "/shp/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Product pr = null;
        try{
            pr = productService.findById(id);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(pr == null){
            response.put("message",
                    "EL producto con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", pr);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    
    @Secured({"ROLE_GROCER","ROLE_ADMIN"})
    @PostMapping(path = "/ctp")
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        Product pr = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            pr = productService.save(product);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El producto ha sido creado con exito");
        response.put("data",pr);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @Secured({"ROLE_GROCER","ROLE_ADMIN"})
    @PutMapping(value = "/udp/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result,
                                    @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Product current = productService.findById(id);
        Product updateProduct = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (current == null) {
            response.put("message", "Error no se pudo editar el producto con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            current.setName(product.getName());
            current.setDescription(product.getDescription());
            current.setBrand(product.getBrand());
            current.setCategory(product.getCategory());
            current.setCode(product.getCode());
            current.setImage(product.getImage());
            current.setPurchase_price(product.getPurchase_price());
            current.setSelling_price(product.getSelling_price());
            updateProduct = productService.save(current);
            response.put("message", "El producto ha sido actualizado con exito!");
            response.put("data", updateProduct);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured({"ROLE_GROCER","ROLE_ADMIN"})
    @DeleteMapping(value = "/dp/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            Product pr = productService.findById(id);
            productService.deleteById(id);
            response.put("message", "El producto ha sido eliminado con exito!");
            response.put("data", pr);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar el producto de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Secured({"ROLE_GROCER","ROLE_ADMIN"})
    @GetMapping(value = "/fpbn/{nterm}")
    public ResponseEntity<?> filterProductByName(@PathVariable String nterm){
        Map<String, Object> response = new HashMap<>();
        try{
            List<Product> l = productService.findProductByName(nterm);
            response.put("data",l);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error", "Error al realizar la consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Secured({"ROLE_GROCER","ROLE_ADMIN"})
    @GetMapping(value = "/fpbc/{cterm}")
    public ResponseEntity<?> filterProductByCode(@PathVariable String cterm){
        Map<String, Object> response = new HashMap<>();
        try{
            List<Product> l = productService.findProductByCode(cterm);
            response.put("data",l);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error", "Error al realizar la consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
