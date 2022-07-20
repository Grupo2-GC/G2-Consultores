package com.fisi.sgapcbackend.services;

import java.util.Optional;

import com.fisi.sgapcbackend.dto.UserDTO;
import com.fisi.sgapcbackend.entities.User;
import com.fisi.sgapcbackend.response.UserResponse;

public interface IUserService extends IGenericCRUD<UserDTO, Long>{
	Optional<User> findUserByUsername(String username);
	Optional<User> findUserByUsernameOrEmail(String username, String email);
	Optional<User> findUserfindByEmail(String email);
    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);
	public UserResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir);
	public UserDTO addRoleToUser(Long userId, Long roleId);
	
}
