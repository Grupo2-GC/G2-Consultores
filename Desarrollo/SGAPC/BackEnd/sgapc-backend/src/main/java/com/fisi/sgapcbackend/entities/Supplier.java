package com.fisi.sgapcbackend.entities;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
//import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telephone;
    @Column(unique = true, length = 12)
    private String ruc;
    @Column(length = 60)
    private String address;
    @Column(length = 10)
    private String pc;
    @Column(unique = true, length = 120)
    private String businessname;

    @NotEmpty(message = "no puede estar vacio")
    @Email(message = "no es una dirección de correo bien formada")
    @Column(nullable = false, unique = true, length = 60)
    private String email;

    //@JsonIgnoreProperties(value={"supplier", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "supplier", cascade = CascadeType.ALL)
    //private List<Entry> entries;

    @Column(name = "create_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createAt;
    @Column(name = "update_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updateAt;
}
