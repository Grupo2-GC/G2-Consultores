package com.fisi.sgapcbackend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fisi.sgapcbackend.entities.Withdrawal;
import com.fisi.sgapcbackend.repositories.IWithdrawalRepository;
import com.fisi.sgapcbackend.services.IWithdrawalService;

import java.util.List;

@Service
public class WithdrawalServiceImpl implements IWithdrawalService {

    @Autowired
    private IWithdrawalRepository repo;

    @Override
    public List<Withdrawal> getAll() {
        return repo.findAll();
    }

    @Override
    public Withdrawal findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Withdrawal save(Withdrawal withdrawal) {
        return repo.save(withdrawal);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

	@Override
	public Page<Withdrawal> getAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(pageable);
	}
}
