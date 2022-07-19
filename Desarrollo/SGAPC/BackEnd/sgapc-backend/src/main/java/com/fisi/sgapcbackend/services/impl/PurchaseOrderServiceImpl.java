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

import com.fisi.sgapcbackend.dto.PurchaseOrderDTO;
import com.fisi.sgapcbackend.entities.PurchaseOrder;
import com.fisi.sgapcbackend.entities.Supplier;
import com.fisi.sgapcbackend.entities.User;
import com.fisi.sgapcbackend.exceptions.ResourceNotFoundException;
import com.fisi.sgapcbackend.repositories.IPurchaseOrderRepository;
import com.fisi.sgapcbackend.repositories.ISupplierRepository;
import com.fisi.sgapcbackend.repositories.IUserRepository;
import com.fisi.sgapcbackend.response.PurchaseOrderResponse;
import com.fisi.sgapcbackend.services.IPurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService{
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Autowired
	private IPurchaseOrderRepository repo;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private ISupplierRepository supplierRepository;

	@Override
	public PurchaseOrderDTO save(PurchaseOrderDTO purchaseOrderDTO) {
		PurchaseOrder purchaseOrder = mapToEntity(purchaseOrderDTO);
		PurchaseOrder newPurchaseOrder = repo.save(purchaseOrder);
		PurchaseOrderDTO poResponse = mapToDTO(newPurchaseOrder);
		return poResponse;
	}

	@Override
	public PurchaseOrderDTO findById(Long id) {
		PurchaseOrder purchaseOrder = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Orden de Compra", "id", id));
		return mapToDTO(purchaseOrder);
	}

	@Override
	public PurchaseOrderDTO update(PurchaseOrderDTO t, Long id) {
		PurchaseOrder purchaseOrder = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Orden de Compra", "id", id));
		
		purchaseOrder.setDateDelivery(t.getDateDelivery());
		purchaseOrder.setDateOrder(t.getDateOrder());
		purchaseOrder.setNum_receipt(t.getNum_receipt());
		purchaseOrder.setPurchaseroderdetail(t.getPurchaseorderDetail());
		purchaseOrder.setSupplier(t.getSupplier());
		purchaseOrder.setUser(t.getUser());

		PurchaseOrder updatePurchaseOrder = repo.save(purchaseOrder);
		return mapToDTO(updatePurchaseOrder);
	}
	
	//Convierte entidad a DTO
 	private PurchaseOrderDTO mapToDTO(PurchaseOrder purchaseOrder) {
 		PurchaseOrderDTO purchaseOrderDTO = modelMapper.map(purchaseOrder, PurchaseOrderDTO.class);
 		return purchaseOrderDTO;
 	}

 	// Convierte de DTO a Entidad
 	private PurchaseOrder mapToEntity(PurchaseOrderDTO purchaseOrderDTO) {
 		PurchaseOrder purchaseOrder = modelMapper.map(purchaseOrderDTO, PurchaseOrder.class);
 		return purchaseOrder;
 	}

	@Override
	public void deleteById(Long id) {
		PurchaseOrder purchaseOrder = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Orden de Compra", "id", id));
		
		
		if(purchaseOrder.getSupplier()==null && purchaseOrder.getUser()==null) {
			repo.delete(purchaseOrder);
		}
		
		if(purchaseOrder.getSupplier()== null) {
			User user = userRepository.findById(purchaseOrder.getUser().getId()).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
			repo.delete(purchaseOrder);
			userRepository.save(user);
		}
		
		if(purchaseOrder.getUser()==null) {
			Supplier supplier = supplierRepository.findById(purchaseOrder.getSupplier().getId()).orElseThrow(() -> new ResourceNotFoundException("Proveedor", "id", id));
			repo.delete(purchaseOrder);
			supplierRepository.save(supplier);
		}
		
		if(purchaseOrder.getSupplier()!=null && purchaseOrder.getUser()!=null) {
			User user = userRepository.findById(purchaseOrder.getUser().getId()).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
			Supplier supplier = supplierRepository.findById(purchaseOrder.getSupplier().getId()).orElseThrow(() -> new ResourceNotFoundException("Proveedor", "id", id));
			repo.delete(purchaseOrder);
			supplierRepository.save(supplier);
			userRepository.save(user);
		}
		
	}
	
	

	@Override
	public PurchaseOrderResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortPeer).ascending()
				: Sort.by(sortPeer).descending();
		Pageable pageable = PageRequest.of(numberOfPage, sizeOfPage, sort);

		Page<PurchaseOrder> purchaseorderes = repo.findAll(pageable);

		List<PurchaseOrder> listOfPurchaseOrderes = purchaseorderes.getContent();
		List<PurchaseOrderDTO> content = listOfPurchaseOrderes.stream().map(entry -> mapToDTO(entry)).collect(Collectors.toList());
		
		PurchaseOrderResponse purchaseOrderResponse = new PurchaseOrderResponse();
		purchaseOrderResponse.setContent(content);
		purchaseOrderResponse.setNumberOfPage(purchaseorderes.getNumber());
		purchaseOrderResponse.setSizeOfPage(purchaseorderes.getSize());
		purchaseOrderResponse.setTotalElements(purchaseorderes.getTotalElements());
		purchaseOrderResponse.setTotalPages(purchaseorderes.getTotalPages());
		purchaseOrderResponse.setFin(purchaseorderes.isLast());
		
		return purchaseOrderResponse;
	}

}
