package com.fisi.sgapcbackend.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fisi.sgapcbackend.entities.User;

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
public class RoleDTO {
	private Long id;
	private String name;
	@JsonIgnore
    private Set<User> users = new HashSet<>();
}
