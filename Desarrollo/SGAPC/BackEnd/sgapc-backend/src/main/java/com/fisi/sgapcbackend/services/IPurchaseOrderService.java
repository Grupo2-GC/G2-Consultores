package com.fisi.sgapcbackend.services;

import com.fisi.sgapcbackend.dto.PurchaseOrderDTO;

import com.fisi.sgapcbackend.response.PurchaseOrderResponse;

public interface IPurchaseOrderService extends IGenericCRUD<PurchaseOrderDTO, Long>{
	
	public PurchaseOrderResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir);


}
