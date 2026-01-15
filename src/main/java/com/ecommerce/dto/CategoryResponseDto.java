package com.ecommerce.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryResponseDto {
	List<CategoryRequestDto> content = new ArrayList<>();
	private Integer pageSize;
	private Integer pageNumber;
	private Long totalElements;
	private Integer totalPages;
	private boolean lastPage;
}
