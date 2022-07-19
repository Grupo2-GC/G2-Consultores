package com.fisi.sgapcbackend.services;

import java.util.List;


import com.fisi.sgapcbackend.dto.CustomerDTO;
import com.fisi.sgapcbackend.response.CustomerResponse;


public interface ICustomerService extends IGenericCRUD<CustomerDTO, Long>{
	
	public CustomerResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir);
	
	public List<CustomerDTO> findCustomerByFullname(String firstname, String lastname);
	
	public List<CustomerDTO> findCustomerByDni(String term);
}
