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

import com.fisi.sgapcbackend.dto.SupplierDTO;
import com.fisi.sgapcbackend.response.SupplierResponse;
import com.fisi.sgapcbackend.services.ISupplierService;
import com.fisi.sgapcbackend.utils.Constants;

@CrossOrigin(origins = {"*", "http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class SupplierController {

	@Autowired
	private ISupplierService supplierService;

	@Secured({ "ROLE_ADMIN", "ROLE_GROCER", "ROLE_LOGISTIC" })
	@GetMapping(path = "/suppliers")
	public ResponseEntity<?> getAll(
			@RequestParam(value = "pageNo", defaultValue = Constants.NUMBER_OF_PAGE_PEER_DEFAULT, required = false) int numberOfPage,
			@RequestParam(value = "pageSize", defaultValue = Constants.SIZE_OF_PAGE_PEER_DEFAULT, required = false) int sizeOfPage,
			@RequestParam(value = "sortBy", defaultValue = Constants.SORT_PEER_DEFAULT, required = false) String sortPeer,
			@RequestParam(value = "sortDir", defaultValue = Constants.SORT_DIRECTION_PEER_DEFAULT, required = false) String sortDir) {

		Map<String, Object> response = new HashMap<>();

		try {
			SupplierResponse supplierResponse = supplierService.getAll(numberOfPage, sizeOfPage, sortPeer, sortDir);
			response.put("data", supplierResponse);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.put("error", "Error al realizar la consulta a la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Secured({ "ROLE_ADMIN", "ROLE_GROCER", "ROLE_LOGISTIC" })
	@GetMapping(path = "/supplier")
	public ResponseEntity<?> show(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "ruc", required = false) String ruc) {
		Map<String, Object> response = new HashMap<>();
		List<SupplierDTO> sr = new ArrayList<SupplierDTO>();
		try {
			if (id != null && ruc == null || id != null) {
				SupplierDTO s = supplierService.findById(id);
				sr.add(s);

			}
			if (ruc != null && id == null) {
				sr = supplierService.findSupplierByRuc(ruc);
			}
		} catch (DataAccessException e) {
			response.put("message", "Error al realizar la consulta a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (id == null && ruc  == null) {
            response.put("message","No ingreso los datos necesarios para filtrar las marcas(id o o dni del cliente)");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", sr);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_GROCER" })
	@PostMapping(path = "/supplier")
	public ResponseEntity<?> create(@Valid @RequestBody SupplierDTO supplierDTO, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		SupplierDTO s = null;

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			s = supplierService.save(supplierDTO);
		} catch (DataAccessException e) {
			response.put("message", "Error al realizar la consulta a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "El proveedor ha sido creado con exito!");
		response.put("data", s);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_GROCER" })
	@PutMapping(path = "/supplier")
	public ResponseEntity<?> update(@Valid @RequestBody SupplierDTO supplierDTO, BindingResult result,
			@RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		SupplierDTO responseSupplier = supplierService.update(supplierDTO, id);

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if (responseSupplier == null) {
			response.put("message", "Error no se pudo editar el proveedor con ID: "
					.concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			response.put("message", "El cliente ha sido actualizado con exito!");
			response.put("data", responseSupplier);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("message", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured({ "ROLE_ADMIN", "ROLE_GROCER" })
	@DeleteMapping(path = "/supplier")
	public ResponseEntity<?> delete(@RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			SupplierDTO s = supplierService.findById(id);
			supplierService.deleteById(id);
			response.put("message", "El proveedor ha sido eliminado con exito!");
			response.put("data", s);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("message", "Error al eliminar el proveedor de la base de datos de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
