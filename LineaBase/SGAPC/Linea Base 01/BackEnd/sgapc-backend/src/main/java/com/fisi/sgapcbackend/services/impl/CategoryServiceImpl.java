package com.fisi.sgapcbackend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fisi.sgapcbackend.entities.Category;
import com.fisi.sgapcbackend.repositories.ICategoryRepository;
import com.fisi.sgapcbackend.services.ICategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private ICategoryRepository repo;

    @Override
    @Transactional(readOnly=true)
    public List<Category> getAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly=true)
    public Category findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Category save(Category category) {
        return repo.save(category);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
