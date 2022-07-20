package com.fisi.sgapcbackend.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fisi.sgapcbackend.dto.ProductDTO;
import com.fisi.sgapcbackend.entities.Brand;
import com.fisi.sgapcbackend.entities.Category;
import com.fisi.sgapcbackend.entities.Product;
import com.fisi.sgapcbackend.exceptions.ResourceNotFoundException;
import com.fisi.sgapcbackend.repositories.IBrandRepository;
import com.fisi.sgapcbackend.repositories.ICategoryRepository;
import com.fisi.sgapcbackend.repositories.IProductRepository;
import com.fisi.sgapcbackend.response.ProductResponse;
import com.fisi.sgapcbackend.services.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IProductRepository repo;
	
	@Autowired
	private ICategoryRepository categoryRepository;
	
	@Autowired
	private IBrandRepository brandRepository;

	@Override
	public ProductDTO findById(Long id) {
		Product product = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
		return mapToDTO(product);
	}

	@Override
	public ProductDTO save(ProductDTO productDTO) {
		Product product = mapToEntity(productDTO);
		Product newProduct = repo.save(product);
		ProductDTO productResponse = mapToDTO(newProduct);
		return productResponse;
	}

	@Override
	public ProductDTO update(ProductDTO t, Long id) {
		Product product = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));

		product.setName(t.getName());
		product.setDescription(t.getDescription());
		product.setCategory(t.getCategory());
		product.setBrand(t.getBrand());
		product.setCode(t.getCode());
		product.setName(t.getName());
		product.setStock(t.getStock());
		product.setPurchase_price(t.getPurchase_price());
		product.setSelling_price(t.getSelling_price());

		Product updateProduct = repo.save(product);
		return mapToDTO(updateProduct);
	}

	@Override
	public void deleteById(Long id) {
		Product product = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
		
		if(product.getBrand() == null && product.getCategory()== null) {
			repo.delete(product);
		}
		
		if(product.getBrand() == null ) {
			Category category = categoryRepository.findById(product.getCategory().getId()).orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", id));
			repo.delete(product);
			categoryRepository.save(category);
		}
		
		if(product.getCategory() == null) {
			Brand brand = brandRepository.findById(product.getBrand().getId()).orElseThrow(() -> new ResourceNotFoundException("Marca", "id", id));
			repo.delete(product);
			brandRepository.save(brand);
		}
		
		if(product.getBrand()!=null && product.getCategory()!=null) {
			Brand brand = brandRepository.findById(product.getBrand().getId()).orElseThrow(() -> new ResourceNotFoundException("Marca", "id", id));
			Category category = categoryRepository.findById(product.getCategory().getId()).orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", id));
			repo.delete(product);
			brandRepository.save(brand);
			categoryRepository.save(category);
		}

	}

	@Override
	public List<ProductDTO> findProductByName(String term) {
		List<Product> products = repo.findByNameContainingIgnoreCase(term);
		return products.stream().map(product -> mapToDTO(product)).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> findProductByCode(String term) {
		List<Product> products = repo.findByCodeContainingIgnoreCase(term);
		return products.stream().map(product -> mapToDTO(product)).collect(Collectors.toList());
	}

	// Convierte entidad a DTO
	private ProductDTO mapToDTO(Product product) {
		ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
		return productDTO;
	}

	// Convierte de DTO a Entidad
	private Product mapToEntity(ProductDTO productDTO) {
		Product product = modelMapper.map(productDTO, Product.class);
		return product;
	}

	@Override
    @Transactional(readOnly=true)
	public ProductResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortPeer).ascending()
				: Sort.by(sortPeer).descending();
		Pageable pageable = PageRequest.of(numberOfPage, sizeOfPage, sort);

		Page<Product> products = repo.findAll(pageable);

		List<Product> listOfProducts = products.getContent();
		List<ProductDTO> content = listOfProducts.stream().map(product -> mapToDTO(product)).collect(Collectors.toList());
		
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(content);
		productResponse.setNumberOfPage(products.getNumber());
		productResponse.setSizeOfPage(products.getSize());
		productResponse.setTotalElements(products.getTotalElements());
		productResponse.setTotalPages(products.getTotalPages());
		productResponse.setFin(products.isLast());
		
		return productResponse;
	}

}
