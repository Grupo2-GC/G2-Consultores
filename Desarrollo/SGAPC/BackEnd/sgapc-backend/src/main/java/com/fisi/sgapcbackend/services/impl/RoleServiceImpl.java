package com.fisi.sgapcbackend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fisi.sgapcbackend.entities.Role;
import com.fisi.sgapcbackend.repositories.IRoleRepository;
import com.fisi.sgapcbackend.services.IRoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository repo;

    @Override
    public List<Role> getAll() {
        return repo.findAll();
    }

    @Override
    public Role findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Role save(Role role) {
        return repo.save(role);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
