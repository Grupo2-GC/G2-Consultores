package com.fisi.sgapcbackend.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fisi.sgapcbackend.entities.PurchaseOrderDetail;
import com.fisi.sgapcbackend.entities.Supplier;
import com.fisi.sgapcbackend.entities.User;

import lombok.Data;

@Data
public class PurchaseOrderDTO {
	private Long id;
	private Supplier supplier;
    private LocalDateTime dateOrder;
    private LocalDateTime dateDelivery;
	private User user;
    private Set<PurchaseOrderDetail> purchaseorderDetail;
    private String num_receipt;
    private Float total;
}
