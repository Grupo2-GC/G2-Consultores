package com.fisi.sgapcbackend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fisi.sgapcbackend.entities.Brand;
import com.fisi.sgapcbackend.repositories.IBrandRepository;
import com.fisi.sgapcbackend.services.IBrandService;

import java.util.List;

@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private IBrandRepository repo;

    @Override
    public List<Brand> getAll() {
        return repo.findAll();
    }

    @Override
    public Brand findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Brand save(Brand brand) {
        return repo.save(brand);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
