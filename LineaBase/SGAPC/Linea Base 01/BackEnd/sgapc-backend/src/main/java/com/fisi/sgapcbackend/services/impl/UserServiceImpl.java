package com.fisi.sgapcbackend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fisi.sgapcbackend.entities.User;
import com.fisi.sgapcbackend.repositories.IUserRepository;
import com.fisi.sgapcbackend.services.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private IUserRepository repo;

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}

	@Override
	public User save(User t) {
		// TODO Auto-generated method stub
		return repo.save(t);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
		
	}

	@Override
	public Boolean existsUserByUsername(String username) {
		// TODO Auto-generated method stub
		return repo.existsByUsername(username);
	}

	@Override
	public Boolean existsUserByEmail(String email) {
		// TODO Auto-generated method stub
		return repo.existsByEmail(email);
	}

	@Override
	public Optional<User> findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return repo.findByUsername(username);
	}

	@Override
	public Optional<User> findUserByUsernameOrEmail(String username, String email) {
		// TODO Auto-generated method stub
		return repo.findByUsernameOrEmail(username, email);
	}

	@Override
	public Optional<User> findUserfindByEmail(String email) {
		// TODO Auto-generated method stub
		return repo.findByEmail(email);
	}
	
	@Override
	public Page<User> getAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(pageable);
	}



}
