package com.fisi.sgapcbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.PurchaseOrder;

@Repository
public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long>{

}
