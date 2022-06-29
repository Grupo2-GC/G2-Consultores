package com.fisi.sgapcbackend.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fisi.sgapcbackend.entities.Product;

public interface IProductService extends IGenericCRUD<Product,Long>{
	
	public Page<Product> getAll(Pageable pageable);
	
	public List<Product> findProductByName(String term);
	
	public List<Product> findProductByCode(String term);

}
