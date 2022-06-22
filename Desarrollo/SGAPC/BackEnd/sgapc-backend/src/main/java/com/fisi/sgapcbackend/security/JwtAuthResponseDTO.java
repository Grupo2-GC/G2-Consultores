package com.fisi.sgapcbackend.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponseDTO {
	private String tokenDeAcceso;
	private String tipoDeToken = "Bearer";
	
	public JwtAuthResponseDTO(String token) {
		this.tokenDeAcceso = token;
	}

}
