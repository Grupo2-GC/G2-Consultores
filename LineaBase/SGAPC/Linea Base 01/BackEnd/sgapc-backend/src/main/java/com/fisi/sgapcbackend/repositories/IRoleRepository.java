package com.fisi.sgapcbackend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
	
	public Optional<Role> findByName(String name);
}
