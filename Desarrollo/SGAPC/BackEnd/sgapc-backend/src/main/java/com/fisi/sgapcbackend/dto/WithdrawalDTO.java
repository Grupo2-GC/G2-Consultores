package com.fisi.sgapcbackend.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fisi.sgapcbackend.entities.Customer;
import com.fisi.sgapcbackend.entities.User;
import com.fisi.sgapcbackend.entities.WithdrawalDetail;

import lombok.Data;

@Data
public class WithdrawalDTO {
	
	private Long id;
    private Customer customer;
    private LocalDateTime date_withdrawal;
    private Float total;
    private User user;
    private Float igv;
    private List<WithdrawalDetail> withdrawalDetails;

}
