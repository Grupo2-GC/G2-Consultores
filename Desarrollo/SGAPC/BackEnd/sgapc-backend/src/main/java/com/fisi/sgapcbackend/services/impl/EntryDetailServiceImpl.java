package com.fisi.sgapcbackend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fisi.sgapcbackend.entities.EntryDetail;
import com.fisi.sgapcbackend.repositories.IEntryDetailRepository;
import com.fisi.sgapcbackend.services.IEntryDetailService;

import java.util.List;

@Repository
public class EntryDetailServiceImpl implements IEntryDetailService {

    @Autowired
    private IEntryDetailRepository repo;

    @Override
    public List<EntryDetail> getAll() {
        return repo.findAll();
    }

    @Override
    public EntryDetail findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public EntryDetail save(EntryDetail entryDetail) {
        return repo.save(entryDetail);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
