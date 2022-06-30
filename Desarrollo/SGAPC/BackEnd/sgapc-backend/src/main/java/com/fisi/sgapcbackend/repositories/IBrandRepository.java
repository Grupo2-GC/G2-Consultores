package com.fisi.sgapcbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.Brand;

@Repository
public interface IBrandRepository extends JpaRepository<Brand, Long> {
}
