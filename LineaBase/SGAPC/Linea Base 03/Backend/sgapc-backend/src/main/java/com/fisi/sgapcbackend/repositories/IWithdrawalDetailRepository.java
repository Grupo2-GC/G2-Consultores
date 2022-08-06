package com.fisi.sgapcbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.Product;
import com.fisi.sgapcbackend.entities.WithdrawalDetail;

@Repository
public interface IWithdrawalDetailRepository extends JpaRepository<WithdrawalDetail, Long> {
	public List<WithdrawalDetail> findByProduct(Product product);

}
