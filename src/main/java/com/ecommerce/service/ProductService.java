package com.ecommerce.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.ProductResponseDto;

public interface ProductService {

	ProductDto addProduct(Long categoryId, ProductDto productDto);
	
	ProductResponseDto getAllProduct();

	ProductResponseDto getProductsByCategory(Long categoryId);

	ProductDto updateProduct(ProductDto productDto, Long productId);

	ProductResponseDto deleteProduct(Long productId);

	ProductResponseDto getProductsByKeyword(String keyword);

	ProductDto updateImage(Long productId, MultipartFile image) throws IOException;

}
