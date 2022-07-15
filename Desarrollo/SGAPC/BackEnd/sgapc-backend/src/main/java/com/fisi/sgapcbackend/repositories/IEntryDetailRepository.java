package com.fisi.sgapcbackend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.EntryDetail;
import com.fisi.sgapcbackend.entities.Product;

@Repository
public interface IEntryDetailRepository extends JpaRepository<EntryDetail, Long> {
	public List<EntryDetail> findByProduct(Product product);
}
