package com.fisi.sgapcbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {
	private Long id;
	private String name;
	private String country;
	private String description;
	private String photo;
}
