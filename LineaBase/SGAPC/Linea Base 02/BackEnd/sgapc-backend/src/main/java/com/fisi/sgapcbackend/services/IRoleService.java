package com.fisi.sgapcbackend.services;

import java.util.Optional;

import com.fisi.sgapcbackend.dto.RoleDTO;
import com.fisi.sgapcbackend.entities.Role;
import com.fisi.sgapcbackend.response.RoleResponse;

public interface IRoleService extends IGenericCRUD<RoleDTO, Long>{
	public RoleResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir);
	
	public Optional<Role> findByName(String name);

}
