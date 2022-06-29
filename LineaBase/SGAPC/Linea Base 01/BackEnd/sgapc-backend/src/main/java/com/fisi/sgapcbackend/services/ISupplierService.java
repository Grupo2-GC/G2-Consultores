package com.fisi.sgapcbackend.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fisi.sgapcbackend.entities.Supplier;

public interface ISupplierService extends IGenericCRUD<Supplier, Long>{
	
	public Page<Supplier> getAll(Pageable pageable);
	
	public List<Supplier> findSupplierByBusinnessName(String term);
	
	public List<Supplier> findSupplierByRuc(String term);

}
