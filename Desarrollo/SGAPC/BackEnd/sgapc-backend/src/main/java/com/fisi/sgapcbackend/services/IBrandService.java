package com.fisi.sgapcbackend.services;


import java.util.List;

import com.fisi.sgapcbackend.dto.BrandDTO;
import com.fisi.sgapcbackend.response.BrandResponse;

public interface IBrandService extends IGenericCRUD<BrandDTO, Long>{
	public BrandResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir);
	public List<BrandDTO> findBrandByName(String name);

}
