package com.fisi.sgapcbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
	
	//Busqueda por nombre
	public List<Product> findByName(String term);
	
	public List<Product> findByNameContainingIgnoreCase(String term);
	
	public List<Product> findByNameStartingWithIgnoreCase(String term);
	
	//Busqueda por codigo
	public List<Product> findByCode(String code);
	
	public List<Product> findByCodeContainingIgnoreCase(String term);
	
	public List<Product> findByCodeStartingWithIgnoreCase(String term);
}
