package com.fisi.sgapcbackend.services;

import com.fisi.sgapcbackend.dto.EntryDetailDTO;
import com.fisi.sgapcbackend.response.EntryDetailResponse;

public interface IEntryDetailService extends IGenericCRUD<EntryDetailDTO, Long>{
	public EntryDetailResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir);

}
