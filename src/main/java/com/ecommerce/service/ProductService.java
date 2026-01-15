package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.ProductResponseDto;

public interface ProductService {

	ProductDto addProduct(Long categoryId, ProductDto productDto);
	
	ProductResponseDto getAllProduct();

	ProductResponseDto getProductsByCategory(Long categoryId);

}
