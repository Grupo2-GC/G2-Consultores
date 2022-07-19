package com.fisi.sgapcbackend.dto;


import com.fisi.sgapcbackend.entities.Product;

import lombok.Data;

@Data
public class PurchaseOrderDetailDTO {
	private Long id;
	private PurchaseOrderDTO purchaseorder;
	private Float price;
	private Integer quantity;
	private Float amount;
    private Product product;
}
