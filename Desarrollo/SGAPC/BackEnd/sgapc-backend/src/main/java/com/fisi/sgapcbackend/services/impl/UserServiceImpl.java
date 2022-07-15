package com.fisi.sgapcbackend.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fisi.sgapcbackend.dto.UserDTO;
import com.fisi.sgapcbackend.entities.Entry;
import com.fisi.sgapcbackend.entities.Role;
import com.fisi.sgapcbackend.entities.User;
import com.fisi.sgapcbackend.exceptions.ResourceNotFoundException;
import com.fisi.sgapcbackend.repositories.IEntryRepository;
import com.fisi.sgapcbackend.repositories.IRoleRepository;
import com.fisi.sgapcbackend.repositories.IUserRepository;
import com.fisi.sgapcbackend.response.UserResponse;
import com.fisi.sgapcbackend.services.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private IUserRepository repo;
	
	@Autowired
	private IEntryRepository entryRepository;
	
	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public UserDTO save(UserDTO userDTO) {
		User user = mapToEntity(userDTO);
		user.setRoles(null);
		System.out.println(user.toString());
		repo.save(user);
		UserDTO userResponse = mapToDTO(user);
		return userResponse;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		User user = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
		return mapToDTO(user);
	}

	@Override
	@Transactional
	public UserDTO update(UserDTO t, Long id) {
		User user = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

		user.setFirstname(t.getFirstname());
		user.setLastname(t.getLastname());
		user.setAddress(t.getAddress());
		user.setDni(t.getDni());
		user.setEmail(t.getEmail());
		user.setAge(t.getAge());
		user.setEnabled(t.getEnabled());
		user.setPassword(t.getPassword());
		user.setTelephone(t.getTelephone());
		user.setUsername(t.getUsername());
		user.setRoles(t.getRoles());
		user.setUpdateAt(t.getUpdateAt());
		user.setCreateAt(t.getCreateAt());
		User updateUser = repo.save(user);
		return mapToDTO(updateUser);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		User user = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
		Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
		
		List<Entry> entriesByUser = entryRepository.findByUser(user);
		
		for(Entry ent : entriesByUser) {
			ent.setUser(null);
			System.out.println("Entrada"+ent.toString());
			entryRepository.save(ent);
		}
		
		user.removeRole(role);
		
		repo.delete(user);
	}

	@Override
	public Optional<User> findUserByUsername(String username) {
		return repo.findByUsername(username);
	}

	@Override
	public Optional<User> findUserByUsernameOrEmail(String username, String email) {
		return repo.findByUsernameOrEmail(username, email);
	}

	@Override
	public Optional<User> findUserfindByEmail(String email) {
		// TODO Auto-generated method stub
		return repo.findByEmail(email);
	}

	@Override
	public Boolean existsUserByUsername(String username) {
		return repo.existsByUsername(username);
	}

	@Override
	public Boolean existsUserByEmail(String email) {
		return repo.existsByEmail(email);
	}

	@Override
	public UserResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortPeer).ascending()
				: Sort.by(sortPeer).descending();
		Pageable pageable = PageRequest.of(numberOfPage, sizeOfPage, sort);

		Page<User> users = repo.findAll(pageable);

		List<User> listOfUsers = users.getContent();
		List<UserDTO> content = listOfUsers.stream().map(user -> mapToDTO(user)).collect(Collectors.toList());
		
		UserResponse userResponse = new UserResponse();
		userResponse.setContent(content);
		userResponse.setNumberOfPage(users.getNumber());
		userResponse.setSizeOfPage(users.getSize());
		userResponse.setTotalElements(users.getTotalElements());
		userResponse.setTotalPages(users.getTotalPages());
		userResponse.setFin(users.isLast());
		
		return userResponse;
	}
	
	
	
	// Convierte entidad a DTO
	private UserDTO mapToDTO(User user) {
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		return userDTO;
	}

	// Convierte de DTO a Entidad
	private User mapToEntity(UserDTO userDTO) {
		User user = modelMapper.map(userDTO, User.class);
		return user;
	}

	@Override
	public UserDTO addRoleToUser(Long userId, Long roleId) {
		User user = repo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", userId));
		Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Rol", "id", roleId));
		user.addRole(role);
		User updateUser = repo.save(user);
		return mapToDTO(updateUser);

	}







}
