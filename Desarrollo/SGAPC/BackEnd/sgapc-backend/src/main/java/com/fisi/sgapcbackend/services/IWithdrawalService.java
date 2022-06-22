package com.fisi.sgapcbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fisi.sgapcbackend.entities.Withdrawal;

public interface IWithdrawalService extends IGenericCRUD<Withdrawal, Long>{
	
	public Page<Withdrawal> getAll(Pageable pageable);
}
