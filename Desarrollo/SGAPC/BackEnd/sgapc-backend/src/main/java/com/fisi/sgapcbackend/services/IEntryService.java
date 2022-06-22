package com.fisi.sgapcbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fisi.sgapcbackend.entities.Entry;

public interface IEntryService extends IGenericCRUD<Entry, Long>{
	
	public Page<Entry> getAll(Pageable pageable);

}
