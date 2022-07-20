package com.fisi.sgapcbackend.services;

import com.fisi.sgapcbackend.dto.EntryDTO;
import com.fisi.sgapcbackend.response.EntryResponse;

public interface IEntryService extends IGenericCRUD<EntryDTO, Long>{
	public EntryResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir);

}
