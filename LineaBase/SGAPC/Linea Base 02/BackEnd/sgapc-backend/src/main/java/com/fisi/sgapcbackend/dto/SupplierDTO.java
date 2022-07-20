package com.fisi.sgapcbackend.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class SupplierDTO {
	private Long id;
    private String telephone;
    private String ruc;
    private String address;
    private String pc;
    private String businessname;
    @NotEmpty(message = "no puede estar vacio")
    @Email(message = "no es una dirección de correo bien formada")
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
