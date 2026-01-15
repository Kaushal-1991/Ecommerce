package com.ecommerce.service;

import com.ecommerce.dto.CategoryRequestDto;
import com.ecommerce.dto.CategoryResponseDto;
import com.ecommerce.entity.Category;

public interface CategoryService {

	CategoryRequestDto createCategory(CategoryRequestDto category);

	CategoryResponseDto findAllCategory(Integer pageNumber, Integer pageSize , String sortBy ,String sortOrder);

	String deleteCategory(Long categoryId);

	CategoryRequestDto updateCategory(Category category, Long categoryId);

	CategoryRequestDto findCategory(Long categoryId);


}
