package com.fisi.sgapcbackend.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fisi.sgapcbackend.dto.BrandDTO;
import com.fisi.sgapcbackend.response.BrandResponse;
import com.fisi.sgapcbackend.services.IBrandService;
import com.fisi.sgapcbackend.services.IUploadPhotoBrandService;
import com.fisi.sgapcbackend.utils.Constants;

@CrossOrigin(origins = {"*", "http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class BrandController {
	
	@Autowired
    private IBrandService brandService;
	
	@Autowired
	private IUploadPhotoBrandService uploadPhotoBrandService;
	
    @Secured({"ROLE_ADMIN","ROLE_GROCER"})
	@GetMapping(path = "/brands")
    public ResponseEntity<?> getAll(
    		@RequestParam(value = "pageNo", defaultValue = Constants.NUMBER_OF_PAGE_PEER_DEFAULT, required = false) int numberOfPage,
			@RequestParam(value = "pageSize", defaultValue = Constants.SIZE_OF_PAGE_PEER_DEFAULT, required = false) int sizeOfPage,
			@RequestParam(value = "sortBy", defaultValue = Constants.SORT_PEER_DEFAULT, required = false) String sortPeer,
			@RequestParam(value = "sortDir", defaultValue = Constants.SORT_DIRECTION_PEER_DEFAULT, required = false) String sortDir){
    	
    	Map<String, Object> response = new HashMap<>();
    	
        try{
        	BrandResponse brandResponse = brandService.getAll(numberOfPage, sizeOfPage, sortPeer, sortDir);
            response.put("data",brandResponse);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

        }catch (Exception e){
            response.put("error", "Error al realizar la consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    	
    }

    @Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @GetMapping(path = "/brand")
    public ResponseEntity<?> show(
    		@RequestParam(value="id", required=false) Long id,
    		@RequestParam(value = "name", required = false) String nameOfBrand){
        Map<String, Object> response = new HashMap<>();
        List<BrandDTO> br= new ArrayList<BrandDTO>();

        try {
        	if(id!=null && nameOfBrand==null || id!=null) {
        		BrandDTO b = brandService.findById(id);
        		br.add(b);
        		//response.put("data", b);

        	}
        	if(nameOfBrand!=null && id==null) {
        		br = brandService.findBrandByName(nameOfBrand);
               // response.put("data", b);

        	}
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (id == null && nameOfBrand  == null) {
            response.put("message","No ingreso los datos necesarios para filtrar las marcas(id o nombre de la marca)");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", br);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    
    @Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @PostMapping(path="/brand")
    public ResponseEntity<?> create(@Valid @RequestBody BrandDTO brandDTO,BindingResult result){
        Map<String, Object> response = new HashMap<>();
        BrandDTO b = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            b = brandService.save(brandDTO);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "La marca ha sido creada con exito!");
        response.put("data",b);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @PutMapping(path = "/brand")
    public ResponseEntity<?> update(@Valid @RequestBody BrandDTO brandDTO, BindingResult result,
    		@RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();
		BrandDTO responseBrand = brandService.update(brandDTO, id);

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() +"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (responseBrand == null) {
            response.put("message", "Error no se pudo editar la marca con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            response.put("message", "La marca ha sido actualizada con exito!");
            response.put("data", responseBrand);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @DeleteMapping(path = "/brand")
    public ResponseEntity<?> delete(@RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            BrandDTO b = brandService.findById(id);
            brandService.deleteById(id);
            response.put("message", "La marca ha sido eliminada con exito!");
            response.put("data", b);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar la marca de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @PostMapping(path = "/brand/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id)
			throws IOException {
		Map<String, Object> response = new HashMap<>();

		BrandDTO b = brandService.findById(id);

		if (!file.isEmpty()) {
			String fileName = null;
			try {
				fileName = uploadPhotoBrandService.copy(file);
			} catch (IOException e) {
				response.put("message", "Error al subir la imagen de la marca");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String lastNamePhoto = b.getPhoto();

			uploadPhotoBrandService.delete(lastNamePhoto);

			b.setPhoto(fileName);

			brandService.save(b);
			response.put("data", b);
			response.put("message", "Has subido correctamente la imagen: " + fileName);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
    
    @Secured({"ROLE_ADMIN","ROLE_GROCER"})
    @GetMapping(value = "/brand/brandsfiles/image/{namePhoto:..+}")
	public ResponseEntity<Resource> showPhoto(@PathVariable String namePhoto) {

		Resource resource = null;
		try {
			resource = uploadPhotoBrandService.upload(namePhoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);

	}

}
