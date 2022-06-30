package com.fisi.sgapcbackend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fisi.sgapcbackend.entities.WithdrawalDetail;
import com.fisi.sgapcbackend.repositories.IWithdrawalDetailRepository;
import com.fisi.sgapcbackend.services.IWithdrawalDetailService;

import java.util.List;

@Service
public class WithdrawalDetailServiceImpl implements IWithdrawalDetailService {

    @Autowired
    private IWithdrawalDetailRepository repo;

    @Override
    public List<WithdrawalDetail> getAll() {
        return repo.findAll();
    }

    @Override
    public WithdrawalDetail findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public WithdrawalDetail save(WithdrawalDetail withdrawalDetail) {
        return repo.save(withdrawalDetail);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
