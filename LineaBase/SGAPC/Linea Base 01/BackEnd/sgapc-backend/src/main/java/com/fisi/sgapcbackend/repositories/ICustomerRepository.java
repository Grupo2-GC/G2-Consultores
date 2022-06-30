package com.fisi.sgapcbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.Customer;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
	
	//Busqueda por nombre
	public List<Customer> findByFirstnameAndLastname(String firstname, String lastname);
	
	public List<Customer> findByFirstnameAndLastnameContainingIgnoreCase(String firstname, String lastname);

	public List<Customer> findByFirstnameAndLastnameStartingWithIgnoreCase(String firstname, String lastname);
	
	//Busqueda por DNI

	public List<Customer> findByDni(String firstname);
		
	public List<Customer> findByDniContainingIgnoreCase(String term);

	public List<Customer> findByDniStartingWithIgnoreCase(String term);
}
