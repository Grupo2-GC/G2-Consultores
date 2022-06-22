package com.fisi.sgapcbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.Withdrawal;

@Repository
public interface IWithdrawalRepository extends JpaRepository<Withdrawal, Long> {
}
