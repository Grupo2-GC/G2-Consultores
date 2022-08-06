package com.fisi.sgapcbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String nameResource;
	private String nameField;
	private String valueStringField="";
	private Long valueField;
	
	public ResourceNotFoundException(String nameResource, String nameField, Long valueField) {
		super(String.format("%s no encontrada con : %s : '%s'", nameResource, nameField, valueField));
		this.nameResource = nameResource;
		this.nameField = nameField;
		this.valueField = valueField;
	}
	public ResourceNotFoundException(String nameResource, String nameField, String valueField) {
		super(String.format("%s no encontrada con : %s : '%s'", nameResource, nameField, valueField));
		this.nameResource = nameResource;
		this.nameField = nameField;
		this.valueStringField += valueStringField;
	}

}
