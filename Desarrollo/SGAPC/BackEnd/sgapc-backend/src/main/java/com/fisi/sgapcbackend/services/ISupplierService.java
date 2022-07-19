package com.fisi.sgapcbackend.services;

import java.util.List;


import com.fisi.sgapcbackend.dto.SupplierDTO;
import com.fisi.sgapcbackend.response.SupplierResponse;

public interface ISupplierService extends IGenericCRUD<SupplierDTO, Long>{
	
	public SupplierResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir);
	
	public List<SupplierDTO> findSupplierByBusinnessName(String term);
	
	public List<SupplierDTO> findSupplierByRuc(String term);

}
