package com.fisi.sgapcbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 120)
    private String firstname;

    @Column(length = 120)
    private String lastname;

    @Column(length = 30)
    private String telephone;

    @Column(length = 60)
    private String address;

    
    @Column(unique = true, length = 12)
    private String ruc;
    
    @Column(unique = true, length = 12)
    private String dni;

    @NotEmpty(message = "no puede estar vacio")
    @Email(message = "no es una dirección de correo bien formada")
    @Column(nullable = false, unique = true, length = 60)
    private String email;

    @Column(name = "create_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createAt;

    @Column(name = "update_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updateAt;
}
