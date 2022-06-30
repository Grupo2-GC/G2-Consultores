package com.fisi.sgapcbackend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fisi.sgapcbackend.entities.Product;
import com.fisi.sgapcbackend.repositories.IProductRepository;
import com.fisi.sgapcbackend.services.IProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository repo;

    @Override
    public List<Product> getAll() {
        return repo.findAll();
    }

    @Override
    public Product findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return repo.save(product);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

	@Override
	public Page<Product> getAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(pageable);
	}

	@Override
	public List<Product> findProductByName(String term) {
		// TODO Auto-generated method stub
		return repo.findByNameContainingIgnoreCase(term);
	}

	@Override
	public List<Product> findProductByCode(String term) {
		// TODO Auto-generated method stub
		return repo.findByCodeContainingIgnoreCase(term);
	}
}
