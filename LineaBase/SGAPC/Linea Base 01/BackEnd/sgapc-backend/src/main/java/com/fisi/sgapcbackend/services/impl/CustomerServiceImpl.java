package com.fisi.sgapcbackend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fisi.sgapcbackend.entities.Customer;
import com.fisi.sgapcbackend.repositories.ICustomerRepository;
import com.fisi.sgapcbackend.services.ICustomerService;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepository repo;

    @Override
    public List<Customer> getAll() {
        return repo.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Customer save(Customer customer) {
        return repo.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

	@Override
	public Page<Customer> getAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(pageable);
	}

	@Override
	public List<Customer> findCustomerByFullname(String firstname, String lastname) {
		// TODO Auto-generated method stub
		return repo.findByFirstnameAndLastname(firstname, lastname);
	}

	@Override
	public List<Customer> findCustomerByDni(String term) {
		// TODO Auto-generated method stub
		return repo.findByDniContainingIgnoreCase(term);
	}
}
