package com.fisi.sgapcbackend.response;

import java.util.List;


import lombok.Data;

@Data
public abstract class Response<T> {
	private List<T> content;
	private int numberOfPage;
	private int sizeOfPage;
	private long totalElements;
	private int totalPages;
	private boolean fin;
}
