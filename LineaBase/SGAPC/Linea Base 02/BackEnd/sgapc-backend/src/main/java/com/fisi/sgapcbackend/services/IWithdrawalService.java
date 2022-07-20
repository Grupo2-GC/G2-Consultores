package com.fisi.sgapcbackend.services;

import com.fisi.sgapcbackend.dto.WithdrawalDTO;
import com.fisi.sgapcbackend.response.WithdrawalResponse;

public interface IWithdrawalService extends IGenericCRUD<WithdrawalDTO, Long>{
	
	public WithdrawalResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir);
}
