package com.fisi.sgapcbackend.dto;

import com.fisi.sgapcbackend.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalDetailDTO {

    private Long id;
    private Product product;
    private Integer quantity_removal;
    private Float selling_price;
    private Float amount;
}
