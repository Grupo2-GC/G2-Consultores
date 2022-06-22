package com.fisi.sgapcbackend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fisi.sgapcbackend.entities.Entry;
import com.fisi.sgapcbackend.repositories.IEntryRepository;
import com.fisi.sgapcbackend.services.IEntryService;

import java.util.List;

@Service
public class EntryServiceImpl implements IEntryService {

    @Autowired
    private IEntryRepository repo;

    @Override
    public List<Entry> getAll() {
        return repo.findAll();
    }

    @Override
    public Entry findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Entry save(Entry entry) {
        return repo.save(entry);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

	@Override
	public Page<Entry> getAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(pageable);
	}
}
