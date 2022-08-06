package com.fisi.sgapcbackend.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fisi.sgapcbackend.dto.RoleDTO;
import com.fisi.sgapcbackend.entities.Role;
import com.fisi.sgapcbackend.entities.User;
import com.fisi.sgapcbackend.exceptions.ResourceNotFoundException;
import com.fisi.sgapcbackend.repositories.IRoleRepository;
import com.fisi.sgapcbackend.response.RoleResponse;
import com.fisi.sgapcbackend.services.IRoleService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IRoleRepository repo;

	@Override
	public RoleDTO findById(Long id) {
		Role role = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rol", "id", id));
		return mapToDTO(role);
	}

	@Override
	public RoleDTO save(RoleDTO roleDTO) {
		Role role = mapToEntity(roleDTO);
		Role newRole = repo.save(role);
		RoleDTO roleResponse = mapToDTO(newRole);
		return roleResponse;
	}

	@Override
	public void deleteById(Long id) {
		Role role = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rol", "id", id));
		
		for(User user: role.getUsers()) {
			role.removeUser(user);
		}
		
		repo.delete(role);
	}

	// Convierte entidad a DTO
	private RoleDTO mapToDTO(Role RoleDTO) {
		RoleDTO roleDTO = modelMapper.map(RoleDTO, RoleDTO.class);
		return roleDTO;
	}

	// Convierte de DTO a Entidad
	private Role mapToEntity(RoleDTO roleDTO) {
		Role role = modelMapper.map(roleDTO, Role.class);
		return role;
	}

	@Override
	public RoleDTO update(RoleDTO t, Long id) {
		Role role = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rol", "id", id));
		role.setName(t.getName());
		Role updateRole = repo.save(role);
		return mapToDTO(updateRole);
	}

	@Override
	@Transactional(readOnly = true)
	public RoleResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortPeer).ascending()
				: Sort.by(sortPeer).descending();
		Pageable pageable = PageRequest.of(numberOfPage, sizeOfPage, sort);

		Page<Role> roles = repo.findAll(pageable);

		List<Role> listOfRoles = roles.getContent();
		List<RoleDTO> content = listOfRoles.stream().map(role -> mapToDTO(role))
				.collect(Collectors.toList());

		RoleResponse roleResponse = new RoleResponse();
		roleResponse.setContent(content);
		roleResponse.setNumberOfPage(roles.getNumber());
		roleResponse.setSizeOfPage(roles.getSize());
		roleResponse.setTotalElements(roles.getTotalElements());
		roleResponse.setTotalPages(roles.getTotalPages());
		roleResponse.setFin(roles.isLast());

		return roleResponse;
	}

	@Override
	public Optional<Role> findByName(String name) {
		return repo.findByName(name);
	}
}
