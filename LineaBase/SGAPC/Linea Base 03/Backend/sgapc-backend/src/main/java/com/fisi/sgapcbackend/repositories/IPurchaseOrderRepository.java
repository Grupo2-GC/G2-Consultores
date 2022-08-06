package com.fisi.sgapcbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.PurchaseOrder;
import com.fisi.sgapcbackend.entities.Supplier;
import com.fisi.sgapcbackend.entities.User;

@Repository
public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long>{
	
	public List<PurchaseOrder> findBySupplier(Supplier supplier);
	public List<PurchaseOrder> findByUser(User user);

}
