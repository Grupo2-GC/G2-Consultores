package com.fisi.sgapcbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 60)
    private String username;
    @Column(length = 120)
    private String firstname;
    @Column(length = 120)
    private String lastname;
    @Column(length = 12)
    private String telephone;

    @NotEmpty(message = "no puede estar vacio")
    @Email(message = "no es una dirección de correo bien formada")
    @Column(nullable = false, unique = true, length = 60)
    private String email;

    private Boolean enabled;
    @Column(unique = true, length = 12)
    private String dni;
    private Integer age;
    @Column(name = "create_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createAt;
    @Column(name = "update_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updateAt;

    private String image;
    @Column(length = 120)
    private String address;
    @Column(length = 60)
    private String password;

	@JsonIgnoreProperties(value={"hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,  CascadeType.MERGE})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();
	
	@JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Entry> entries = new HashSet<Entry>();
	
	
	public void addRole(Role role) {
		this.roles.add(role);
		role.getUsers().add(this);
	}
	
	public void removeRole(Role role) {
		this.roles.remove(role);
		role.getUsers().remove(this);
	}
}
