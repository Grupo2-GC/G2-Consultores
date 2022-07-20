package com.fisi.sgapcbackend.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class CustomerDTO {
	private Long id;
    private String firstname;
    private String lastname;
    private String telephone;
    private String address;
    private String ruc; 
    private String dni;
    @Email
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
