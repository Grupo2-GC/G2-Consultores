package com.fisi.sgapcbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.EntryDetail;

@Repository
public interface IEntryDetailRepository extends JpaRepository<EntryDetail, Long> {
}
