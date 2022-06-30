package com.fisi.sgapcbackend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fisi.sgapcbackend.entities.PurchaseOrder;
import com.fisi.sgapcbackend.repositories.IPurchaseOrderRepository;
import com.fisi.sgapcbackend.services.IPurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService{
	
	@Autowired
	private IPurchaseOrderRepository repo;

	@Override
	public List<PurchaseOrder> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public PurchaseOrder findById(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}

	@Override
	public PurchaseOrder save(PurchaseOrder t) {
		// TODO Auto-generated method stub
		return repo.save(t);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

	@Override
	public Page<PurchaseOrder> getAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(pageable);
	}

}
