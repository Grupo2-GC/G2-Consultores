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

import com.fisi.sgapcbackend.dto.WithdrawalDTO;
import com.fisi.sgapcbackend.response.WithdrawalResponse;
import com.fisi.sgapcbackend.services.IWithdrawalService;
import com.fisi.sgapcbackend.utils.Constants;

@CrossOrigin(origins = {"*", "http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class WithdrawalController {
	
	@Autowired
	private IWithdrawalService withdrawalService;

	@Secured({"ROLE_ADMIN", "ROLE_GROCER","ROLE_LOGISTIC"})
	@GetMapping(path = "/withdrawals")
	public ResponseEntity<?> getAll(
			@RequestParam(value = "pageNo", defaultValue = Constants.NUMBER_OF_PAGE_PEER_DEFAULT, required = false) int numberOfPage,
			@RequestParam(value = "pageSize", defaultValue = Constants.SIZE_OF_PAGE_PEER_DEFAULT, required = false) int sizeOfPage,
			@RequestParam(value = "sortBy", defaultValue = Constants.SORT_PEER_DEFAULT, required = false) String sortPeer,
			@RequestParam(value = "sortDir", defaultValue = Constants.SORT_DIRECTION_PEER_DEFAULT, required = false) String sortDir) {

		Map<String, Object> response = new HashMap<>();

		try {
			WithdrawalResponse withdrawalResponse = withdrawalService.getAll(numberOfPage, sizeOfPage, sortPeer, sortDir);
			response.put("data", withdrawalResponse);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.put("error", "Error al realizar la consulta a la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@Secured({ "ROLE_ADMIN", "ROLE_GROCER","ROLE_LOGISTIC"})
	@GetMapping(path = "/withdrawal")
	public ResponseEntity<?> show(@RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		WithdrawalDTO withdrawalDTO = null;
		try {
			withdrawalDTO = withdrawalService.findById(id);
		} catch (DataAccessException e) {
			response.put("message", "Error al realizar la consulta a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("data", withdrawalDTO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @PostMapping(path="/withdrawal")
    public ResponseEntity<?> create(@Valid @RequestBody WithdrawalDTO withdrawalDTO,BindingResult result){
        Map<String, Object> response = new HashMap<>();
        WithdrawalDTO wdto = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
        	wdto = withdrawalService.save(withdrawalDTO);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "La salida de productos ha sido creada con exito!");
        response.put("data",wdto);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
	
	@Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @PutMapping(path = "/withdrawal")
    public ResponseEntity<?> update(@Valid @RequestBody WithdrawalDTO withdrawalDTO, BindingResult result,
    		@RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();
        WithdrawalDTO responseWithdrawal = withdrawalService.update(withdrawalDTO, id);

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            response.put("message", "La salida de productos ha sido actualizada con exito!");
            response.put("data", responseWithdrawal);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @DeleteMapping(path = "/withdrawal")
    public ResponseEntity<?> delete(@RequestParam(value = "id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
        	WithdrawalDTO wdto = withdrawalService.findById(id);
        	withdrawalService.deleteById(id);
            response.put("message", "La salida de productos ha sido eliminada con exito!");
            response.put("data", wdto);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar la entrada de productos a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	

}
