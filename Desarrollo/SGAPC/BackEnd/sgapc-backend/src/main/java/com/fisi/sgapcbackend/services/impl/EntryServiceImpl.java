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

import com.fisi.sgapcbackend.dto.EntryDTO;
import com.fisi.sgapcbackend.entities.Entry;
import com.fisi.sgapcbackend.entities.Supplier;
import com.fisi.sgapcbackend.entities.User;
import com.fisi.sgapcbackend.exceptions.ResourceNotFoundException;
import com.fisi.sgapcbackend.repositories.IEntryRepository;
import com.fisi.sgapcbackend.repositories.ISupplierRepository;
import com.fisi.sgapcbackend.repositories.IUserRepository;
import com.fisi.sgapcbackend.response.EntryResponse;
import com.fisi.sgapcbackend.services.IEntryService;

@Service
public class EntryServiceImpl implements IEntryService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IEntryRepository repo;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private ISupplierRepository supplierRepository;

	@Override
	@Transactional(readOnly = true)
	public EntryDTO findById(Long id) {
		Entry entry = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Entrada de Producto", "id", id));
		return mapToDTO(entry);
	}

	@Override
	@Transactional
	public EntryDTO save(EntryDTO entryDTO) {
		Entry entry = mapToEntity(entryDTO);
		Entry newEntry = repo.save(entry);
		EntryDTO entryResponse = mapToDTO(newEntry);
		return entryResponse;
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		Entry entry = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Entrada de Producto", "id", id));
		
		//User user = userRepository.findById(entry.getUser().getId()).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
		
		//Supplier supplier = supplierRepository.findById(entry.getSupplier().getId()).orElseThrow(() -> new ResourceNotFoundException("Proveedor", "id", id));
		
		if(entry.getSupplier()==null && entry.getUser()==null) {
			repo.delete(entry);
		}
		
		if(entry.getSupplier()== null) {
			User user = userRepository.findById(entry.getUser().getId()).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
			repo.delete(entry);
			userRepository.save(user);
		}
		
		if(entry.getUser()==null) {
			Supplier supplier = supplierRepository.findById(entry.getSupplier().getId()).orElseThrow(() -> new ResourceNotFoundException("Proveedor", "id", id));
			repo.delete(entry);
			supplierRepository.save(supplier);
		}
		
		if(entry.getSupplier()!=null && entry.getUser()!=null) {
			User user = userRepository.findById(entry.getUser().getId()).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
			Supplier supplier = supplierRepository.findById(entry.getSupplier().getId()).orElseThrow(() -> new ResourceNotFoundException("Proveedor", "id", id));
			repo.delete(entry);
			supplierRepository.save(supplier);
			userRepository.save(user);
		}


	}

	// Convierte entidad a DTO
	private EntryDTO mapToDTO(Entry entry) {
		EntryDTO entryDTO = modelMapper.map(entry, EntryDTO.class);
		return entryDTO;
	}

	// Convierte de DTO a Entidad
	private Entry mapToEntity(EntryDTO entryDTO) {
		Entry entry = modelMapper.map(entryDTO, Entry.class);
		return entry;
	}

	@Override
	@Transactional
	public EntryDTO update(EntryDTO t, Long id) {
		Entry entry = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entrada de Producto", "id", id));

		entry.setDate_entry(t.getDate_entry());
		entry.setDetails(t.getDetails());
		entry.setIgv(t.getIgv());
		entry.setNum_receipt(t.getNum_receipt());
		entry.setSupplier(t.getSupplier());
		entry.setTotal(t.getTotal());
		entry.setType_receipt(t.getType_receipt());
		entry.setUser(t.getUser());
		Entry updateEntry = repo.save(entry);
		return mapToDTO(updateEntry);
	}

	@Override
    @Transactional(readOnly=true)
	public EntryResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortPeer).ascending()
				: Sort.by(sortPeer).descending();
		Pageable pageable = PageRequest.of(numberOfPage, sizeOfPage, sort);

		Page<Entry> entries = repo.findAll(pageable);

		List<Entry> listOfEntries = entries.getContent();
		List<EntryDTO> content = listOfEntries.stream().map(entry -> mapToDTO(entry)).collect(Collectors.toList());
		
		EntryResponse entryResponse = new EntryResponse();
		entryResponse.setContent(content);
		entryResponse.setNumberOfPage(entries.getNumber());
		entryResponse.setSizeOfPage(entries.getSize());
		entryResponse.setTotalElements(entries.getTotalElements());
		entryResponse.setTotalPages(entries.getTotalPages());
		entryResponse.setFin(entries.isLast());
		
		return entryResponse;
	}

}
