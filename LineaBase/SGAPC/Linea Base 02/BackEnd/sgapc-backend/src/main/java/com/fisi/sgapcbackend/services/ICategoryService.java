package com.fisi.sgapcbackend.services;


import java.util.List;

import com.fisi.sgapcbackend.dto.CategoryDTO;
import com.fisi.sgapcbackend.response.CategoryResponse;

public interface ICategoryService extends IGenericCRUD<CategoryDTO, Long>{
	public CategoryResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir);
	
	public List<CategoryDTO> findCategoryByName(String name);
}
