package com.fisi.sgapcbackend.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fisi.sgapcbackend.entities.EntryDetail;
import com.fisi.sgapcbackend.entities.Supplier;
import com.fisi.sgapcbackend.entities.User;

import lombok.Data;

@Data
public class EntryDTO {
	private Long id;
    private Supplier supplier;
    private String type_receipt;
    private Float total;
    private LocalDateTime date_entry;
    private User user;
    private Float igv;
    private String num_receipt;
    private List<EntryDetail> details;

}
