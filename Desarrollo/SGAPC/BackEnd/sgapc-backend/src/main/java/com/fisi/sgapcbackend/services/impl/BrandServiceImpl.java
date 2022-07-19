package com.fisi.sgapcbackend.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fisi.sgapcbackend.dto.BrandDTO;
import com.fisi.sgapcbackend.entities.Brand;
import com.fisi.sgapcbackend.entities.Product;
import com.fisi.sgapcbackend.exceptions.ResourceNotFoundException;
import com.fisi.sgapcbackend.repositories.IBrandRepository;
import com.fisi.sgapcbackend.repositories.IProductRepository;
import com.fisi.sgapcbackend.response.BrandResponse;
import com.fisi.sgapcbackend.services.IBrandService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements IBrandService {
	
	@Autowired
	private ModelMapper modelMapper;

    @Autowired
    private IBrandRepository repo;
    
    @Autowired
    private IProductRepository productRepository;


    @Override
    @Transactional(readOnly=true)
    public BrandDTO findById(Long id) {
        Brand brand = repo.findById(id)
        		.orElseThrow(()-> new ResourceNotFoundException("Marca", "id", id));
        return mapToDTO(brand);
    }

    @Override
    @Transactional
    public BrandDTO save(BrandDTO brandDTO) {
        Brand brand = mapToEntity(brandDTO);
        Brand newBrand = repo.save(brand);
        BrandDTO brandResponse = mapToDTO(newBrand);
        return brandResponse;
        
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Brand brand = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Marca", "id", id));
        List<Product> productsByBrand = productRepository.findByBrand(brand);
        
        for (Product product : productsByBrand) {
			product.setBrand(null);
			productRepository.save(product);
        }
		repo.deleteById(brand.getId());
    }
    
 // Convierte entidad a DTO
 	private BrandDTO mapToDTO(Brand brand) {
 		BrandDTO brandDTO = modelMapper.map(brand, BrandDTO.class);
 		return brandDTO;
 	}

 	// Convierte de DTO a Entidad
 	private Brand mapToEntity(BrandDTO brandDTO) {
 		Brand brand = modelMapper.map(brandDTO, Brand.class);
 		return brand;
 	}

	@Override
	public BrandDTO update(BrandDTO t, Long id) {
		
		Brand brand = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Marca", "id", id));
		
		brand.setCountry(t.getCountry());
		brand.setDescription(t.getDescription());
		brand.setName(t.getName());
		
		Brand updateBrand = repo.save(brand);
		return mapToDTO(updateBrand);
		
	}

	@Override
	public BrandResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortPeer).ascending()
				: Sort.by(sortPeer).descending();
		Pageable pageable = PageRequest.of(numberOfPage, sizeOfPage, sort);

		Page<Brand> brands = repo.findAll(pageable);

		List<Brand> listOfBrands = brands.getContent();
		List<BrandDTO> content = listOfBrands.stream().map(brand -> mapToDTO(brand)).collect(Collectors.toList());
		
		BrandResponse brandResponse = new BrandResponse();
		brandResponse.setContent(content);
		brandResponse.setNumberOfPage(brands.getNumber());
		brandResponse.setSizeOfPage(brands.getSize());
		brandResponse.setTotalElements(brands.getTotalElements());
		brandResponse.setTotalPages(brands.getTotalPages());
		brandResponse.setFin(brands.isLast());
		
		return brandResponse;
	}

	@Override
	public List<BrandDTO> findBrandByName(String name) {
		List<Brand> brands = repo.findByNameContainingIgnoreCase(name);
		List<BrandDTO> content = brands.stream().map(brand -> mapToDTO(brand)).collect(Collectors.toList());
		return content;

	}
}
