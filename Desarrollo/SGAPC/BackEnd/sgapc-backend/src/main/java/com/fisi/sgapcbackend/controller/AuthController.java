package com.fisi.sgapcbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fisi.sgapcbackend.dto.LoginDTO;
import com.fisi.sgapcbackend.security.JwtAuthResponseDTO;
import com.fisi.sgapcbackend.security.JwtTokenProvider;

@CrossOrigin(origins = {"*", "http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	
	@PostMapping(value = "/auth/login")
	public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginDTO, BindingResult result){
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtTokenProvider.generateToken(authentication);
			
			
			String username = ((User) authentication.getPrincipal()).getUsername();
			
			
			
			response.put("message", "Ha iniciado sesion con exito");
			response.put("username", username);
			response.put("authorities",authentication.getAuthorities());
			response.put("token", new JwtAuthResponseDTO(token));
			
			return new ResponseEntity<>(response,HttpStatus.OK);
		} catch (BadCredentialsException  e) {
			 //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			 response.put("message", "Error en las credenciales de acceso");
			 response.put("error", e.getLocalizedMessage());
			 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.UNAUTHORIZED);
		}
		
		
	}

}
