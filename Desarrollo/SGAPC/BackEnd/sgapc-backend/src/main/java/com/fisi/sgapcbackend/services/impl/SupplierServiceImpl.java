package com.fisi.sgapcbackend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fisi.sgapcbackend.entities.Supplier;
import com.fisi.sgapcbackend.repositories.ISupplierRepository;
import com.fisi.sgapcbackend.services.ISupplierService;

import java.util.List;

@Service
public class SupplierServiceImpl implements ISupplierService {

    @Autowired
    private ISupplierRepository repo;

    @Override
    public List<Supplier> getAll() {
        return repo.findAll();
    }

    @Override
    public Supplier findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Supplier save(Supplier supplier) {
        return repo.save(supplier);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

	@Override
	public Page<Supplier> getAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(pageable);
	}

	@Override
	public List<Supplier> findSupplierByBusinnessName(String term) {
		// TODO Auto-generated method stub
		return repo.findByBusinessname(term);
	}

	@Override
	public List<Supplier> findSupplierByRuc(String term) {
		// TODO Auto-generated method stub
		return repo.findByRucContainingIgnoreCase(term);
	}
}
