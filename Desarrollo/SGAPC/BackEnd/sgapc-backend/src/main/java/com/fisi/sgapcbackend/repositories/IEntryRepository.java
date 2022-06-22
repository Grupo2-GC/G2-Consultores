package com.fisi.sgapcbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.Entry;

@Repository
public interface IEntryRepository extends JpaRepository<Entry, Long> {
}
