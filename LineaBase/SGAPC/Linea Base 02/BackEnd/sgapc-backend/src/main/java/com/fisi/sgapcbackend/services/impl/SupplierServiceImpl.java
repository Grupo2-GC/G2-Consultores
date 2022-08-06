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

import com.fisi.sgapcbackend.dto.SupplierDTO;
import com.fisi.sgapcbackend.entities.Supplier;
import com.fisi.sgapcbackend.exceptions.ResourceNotFoundException;
import com.fisi.sgapcbackend.repositories.ISupplierRepository;
import com.fisi.sgapcbackend.response.SupplierResponse;
import com.fisi.sgapcbackend.services.ISupplierService;

@Service
public class SupplierServiceImpl implements ISupplierService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ISupplierRepository repo;

	@Override
	@Transactional(readOnly = true)
	public SupplierDTO findById(Long id) {
		Supplier supplier = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proveedor", "id", id));
		return mapToDTO(supplier);
	}

	@Override
	@Transactional
	public SupplierDTO save(SupplierDTO brandDTO) {
		Supplier supplier = mapToEntity(brandDTO);
		Supplier newSupplier = repo.save(supplier);
		SupplierDTO supplierResponse = mapToDTO(newSupplier);
		return supplierResponse;
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		Supplier supplier = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proveedor", "id", id));
		repo.delete(supplier);
	}

	@Override
	public SupplierDTO update(SupplierDTO t, Long id) {
		Supplier supplier = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proveedor", "id", id));
		
		supplier.setBusinessname(t.getBusinessname());
		supplier.setAddress(t.getAddress());
		supplier.setCreateAt(t.getCreateAt());
		supplier.setEmail(t.getEmail());
		supplier.setRuc(t.getRuc());
		supplier.setTelephone(t.getTelephone());
		supplier.setUpdateAt(t.getUpdateAt());

		Supplier updateSupplier = repo.save(supplier);
		return mapToDTO(updateSupplier);
	}

	@Override
	public List<SupplierDTO> findSupplierByBusinnessName(String term) {
		List<Supplier> suppliers = repo.findByBusinessnameContainingIgnoreCase(term);
		return suppliers.stream().map(supplier -> mapToDTO(supplier)).collect(Collectors.toList());
	}

	@Override
	public List<SupplierDTO> findSupplierByRuc(String term) {
		List<Supplier> suppliers = repo.findByRucContainingIgnoreCase(term);
		return suppliers.stream().map(supplier -> mapToDTO(supplier)).collect(Collectors.toList());
	}

	@Override
	public SupplierResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortPeer).ascending()
				: Sort.by(sortPeer).descending();
		Pageable pageable = PageRequest.of(numberOfPage, sizeOfPage, sort);

		Page<Supplier> suppliers = repo.findAll(pageable);

		List<Supplier> listOfSuppliers = suppliers.getContent();
		List<SupplierDTO> content = listOfSuppliers.stream().map(supplier -> mapToDTO(supplier)).collect(Collectors.toList());
		
		SupplierResponse supplierResponse = new SupplierResponse();
		supplierResponse.setContent(content);
		supplierResponse.setNumberOfPage(suppliers.getNumber());
		supplierResponse.setSizeOfPage(suppliers.getSize());
		supplierResponse.setTotalElements(suppliers.getTotalElements());
		supplierResponse.setTotalPages(suppliers.getTotalPages());
		supplierResponse.setFin(suppliers.isLast());
		
		return supplierResponse;
	}

	// Convierte entidad a DTO
	private SupplierDTO mapToDTO(Supplier supplier) {
		SupplierDTO supplierDTO = modelMapper.map(supplier, SupplierDTO.class);
		return supplierDTO;
	}

	// Convierte de DTO a Entidad
	private Supplier mapToEntity(SupplierDTO supplierDTO) {
		Supplier supplier = modelMapper.map(supplierDTO, Supplier.class);
		return supplier;
	}

}
