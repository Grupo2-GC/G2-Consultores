package com.fisi.sgapcbackend.services;

import java.util.List;

import com.fisi.sgapcbackend.dto.ProductDTO;
import com.fisi.sgapcbackend.response.ProductResponse;

public interface IProductService extends IGenericCRUD<ProductDTO,Long>{
	
	public ProductResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir);
	
	public List<ProductDTO> findProductByName(String term);
	
	public List<ProductDTO> findProductByCode(String term);

}
