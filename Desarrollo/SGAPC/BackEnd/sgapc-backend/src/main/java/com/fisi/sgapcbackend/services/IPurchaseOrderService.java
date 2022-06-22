package com.fisi.sgapcbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fisi.sgapcbackend.entities.PurchaseOrder;

public interface IPurchaseOrderService extends IGenericCRUD<PurchaseOrder, Long>{
	
	public Page<PurchaseOrder> getAll(Pageable pageable);


}
