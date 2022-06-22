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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fisi.sgapcbackend.entities.PurchaseOrder;
import com.fisi.sgapcbackend.services.IPurchaseOrderService;

@RestController
@RequestMapping("/purchase-order")
public class PurchaseOrderController {
	
	@Autowired
	private IPurchaseOrderService purchaseOrderService;
	
    @Secured({"ROLE_LOGISTIC","ROLE_ADMIN"})
	@GetMapping(path = "/glpo")
	public ResponseEntity<?> index (){
		Map<String, Object> response = new HashMap<>();
	
		try {	
			List<PurchaseOrder> l = purchaseOrderService.getAll();
			response.put("data", l);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			
		}catch (Exception e) {
			response.put("error", "Error al realizar la consulta en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
    @Secured({"ROLE_LOGISTIC","ROLE_ADMIN"})
	@GetMapping(value="/shpo/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		PurchaseOrder purchaseOrder = null;
		try {
			purchaseOrder = purchaseOrderService.findById(id);
		}catch (DataAccessException e) {
			response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(purchaseOrder==null) {
			response.put("message",
                    "La order de compra con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("data", purchaseOrder);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@Secured({"ROLE_LOGISTIC","ROLE_ADMIN"})
	@PostMapping(path = "/ctpo")
    public ResponseEntity<?> create(@Valid  @RequestBody PurchaseOrder purchaseOrder, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        PurchaseOrder po = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            po = purchaseOrderService.save(purchaseOrder);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "La orden de compra ha sido creada con exito.");
        response.put("data", po);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @Secured({"ROLE_LOGISTIC","ROLE_ADMIN"})
    @PutMapping(value = "/upo/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody PurchaseOrder purchaseOrder, BindingResult result,
                                    @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        PurchaseOrder current = purchaseOrderService.findById(id);
        PurchaseOrder updatePurchaseOrder = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (current == null) {
            response.put("message", "Error no se pudo editar la orden de compra con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            current.setSupplier(purchaseOrder.getSupplier());
            current.setDateDelivery(purchaseOrder.getDateDelivery());
            current.setDateOrder(purchaseOrder.getDateOrder());
            current.setNum_receipt(purchaseOrder.getNum_receipt());
            current.setPurchaseorderDetail(purchaseOrder.getPurchaseorderDetail());
            current.setTotal(purchaseOrder.getTotal());
            current.setUser(purchaseOrder.getUser());
            updatePurchaseOrder = purchaseOrderService.save(current);
            response.put("message", "La orden de compra ha sido actualizada con exito!");
            response.put("data", updatePurchaseOrder);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured({"ROLE_LOGISTIC","ROLE_ADMIN"})
    @DeleteMapping(value = "/dcu/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            PurchaseOrder po = purchaseOrderService.findById(id);
            purchaseOrderService.deleteById(id);
            response.put("message", "La orden de compra ha sido eliminada con exito!");
            response.put("data", po);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar el cliente de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
