package com.fisi.sgapcbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.Supplier;

@Repository
public interface ISupplierRepository extends JpaRepository<Supplier, Long> {
	
	//Busqueda por nombre del negocio
	
	public List<Supplier> findByBusinessname(String term);
	
	public List<Supplier> findByBusinessnameContainingIgnoreCase(String term);
	
	public List<Supplier> findByBusinessnameStartingWithIgnoreCase(String term);
	
	//Busqueda por RUC
	
	public List<Supplier> findByRuc(String term);
	
	public List<Supplier> findByRucContainingIgnoreCase(String term);
	
	public List<Supplier> findByRucStartingWithIgnoreCase(String term);
}
