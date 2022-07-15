package com.fisi.sgapcbackend.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fisi.sgapcbackend.dto.CategoryDTO;
import com.fisi.sgapcbackend.entities.Category;
import com.fisi.sgapcbackend.entities.Product;
import com.fisi.sgapcbackend.exceptions.ResourceNotFoundException;
import com.fisi.sgapcbackend.repositories.ICategoryRepository;
import com.fisi.sgapcbackend.repositories.IProductRepository;
import com.fisi.sgapcbackend.response.CategoryResponse;
import com.fisi.sgapcbackend.services.ICategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ICategoryRepository repo;
	
	@Autowired
	private IProductRepository productRepository;

	@Override
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Category category = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", id));
		return mapToDTO(category);
	}

	@Override
	@Transactional
	public CategoryDTO save(CategoryDTO categoryDTO) {
		Category category = mapToEntity(categoryDTO);
		Category newCategory = repo.save(category);
		CategoryDTO categoryResponse = mapToDTO(newCategory);
		return categoryResponse;
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		Category category = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", id));

		List<Product> productsByCategory = productRepository.findByCategory(category);

		for (Product product : productsByCategory) {
			product.setCategory(null);
			productRepository.save(product);
		}
		repo.delete(category);
	}

	// Convierte entidad a DTO
	private CategoryDTO mapToDTO(Category category) {
		CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
		return categoryDTO;
	}

	// Convierte de DTO a Entidad
	private Category mapToEntity(CategoryDTO categoryDTO) {
		Category category = modelMapper.map(categoryDTO, Category.class);
		return category;
	}

	@Override
	@Transactional
	public CategoryDTO update(CategoryDTO t, Long id) {
		Category category = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", id));

		category.setName(t.getName());
		category.setDescription(t.getDescription());
		Category updateCategory = repo.save(category);
		return mapToDTO(updateCategory);
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortPeer).ascending()
				: Sort.by(sortPeer).descending();
		Pageable pageable = PageRequest.of(numberOfPage, sizeOfPage, sort);

		Page<Category> categories = repo.findAll(pageable);

		List<Category> listOfCategories = categories.getContent();
		List<CategoryDTO> content = listOfCategories.stream().map(category -> mapToDTO(category))
				.collect(Collectors.toList());

		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setContent(content);
		categoryResponse.setNumberOfPage(categories.getNumber());
		categoryResponse.setSizeOfPage(categories.getSize());
		categoryResponse.setTotalElements(categories.getTotalElements());
		categoryResponse.setTotalPages(categories.getTotalPages());
		categoryResponse.setFin(categories.isLast());

		return categoryResponse;
	}

	@Override
	public List<CategoryDTO> findCategoryByName(String name) {
		List<Category> categories = repo.findByNameContainingIgnoreCase(name);
		// List<Category> listOfCategories = categories.getContent();
		List<CategoryDTO> content = categories.stream().map(category -> mapToDTO(category)).collect(Collectors.toList());
		return content;
	}
}
