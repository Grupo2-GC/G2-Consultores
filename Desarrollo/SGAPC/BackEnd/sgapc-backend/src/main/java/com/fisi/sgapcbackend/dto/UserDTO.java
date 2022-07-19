package com.fisi.sgapcbackend.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fisi.sgapcbackend.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String telephone;
    @NotEmpty(message = "no puede estar vacio")
    @Email(message = "no es una dirección de correo bien formada")
    private String email;
    private Boolean enabled;
    private String dni;
    private Integer age;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String image;
    private String address;
    private String password;
    private Set<Role> roles = new HashSet<>();
}
