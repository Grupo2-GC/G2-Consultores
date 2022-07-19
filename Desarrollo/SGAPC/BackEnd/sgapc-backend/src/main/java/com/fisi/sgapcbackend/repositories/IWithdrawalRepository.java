package com.fisi.sgapcbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.Customer;
import com.fisi.sgapcbackend.entities.User;
import com.fisi.sgapcbackend.entities.Withdrawal;

@Repository
public interface IWithdrawalRepository extends JpaRepository<Withdrawal, Long> {
	
	public List<Withdrawal> findByCustomer(Customer customer);
	public List<Withdrawal> findByUser(User user);
}
