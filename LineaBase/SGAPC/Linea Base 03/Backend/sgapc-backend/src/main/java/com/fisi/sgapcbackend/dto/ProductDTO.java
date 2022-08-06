package com.fisi.sgapcbackend.dto;

import com.fisi.sgapcbackend.entities.Brand;
import com.fisi.sgapcbackend.entities.Category;

import lombok.Data;

@Data
public class ProductDTO {
	private Long id;
    private String name;
    private Float selling_price;
    private String description;
    private Float purchase_price;
    private String code;
    private String image;
    private Category category;
    private Integer stock;
    private Brand brand;
}
