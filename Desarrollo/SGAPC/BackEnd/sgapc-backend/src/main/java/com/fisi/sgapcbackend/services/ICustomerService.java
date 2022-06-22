package com.fisi.sgapcbackend.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fisi.sgapcbackend.entities.Customer;


public interface ICustomerService extends IGenericCRUD<Customer, Long>{
	
	public Page<Customer> getAll(Pageable pageable);
	
	public List<Customer> findCustomerByFullname(String firstname, String lastname);
	
	public List<Customer> findCustomerByDni(String term);
}
