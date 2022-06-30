package com.fisi.sgapcbackend.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fisi.sgapcbackend.entities.User;

public interface IUserService extends IGenericCRUD<User, Long>{
	Optional<User> findUserByUsername(String username);
	Optional<User> findUserByUsernameOrEmail(String username, String email);
	Optional<User> findUserfindByEmail(String email);
    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);
    public Page<User> getAll(Pageable pageable);

}
