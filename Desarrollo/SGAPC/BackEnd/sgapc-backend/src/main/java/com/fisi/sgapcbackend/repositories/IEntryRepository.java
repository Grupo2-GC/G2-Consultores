package com.fisi.sgapcbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.Entry;
import com.fisi.sgapcbackend.entities.Supplier;
import com.fisi.sgapcbackend.entities.User;

@Repository
public interface IEntryRepository extends JpaRepository<Entry, Long> {
	
	public List<Entry> findByUser(User user);
	public List<Entry> findBySupplier(Supplier supplier);
	
}
