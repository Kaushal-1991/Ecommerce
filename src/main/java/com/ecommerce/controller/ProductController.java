package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.ProductResponseDto;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {	
	
	@Autowired
	private ProductService productService;

	@PostMapping("/admin/categories/{categoryId}/product")
	public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto, @PathVariable Long categoryId) {
		ProductDto responseAddProduct = productService.addProduct(categoryId, productDto);
		return new ResponseEntity<ProductDto>(responseAddProduct,HttpStatus.CREATED);
	}
	
	@GetMapping("/public/products")
	public ResponseEntity<ProductResponseDto> getAllPoducts(){
		ProductResponseDto productDto = productService.getAllProduct();
		return new ResponseEntity<>(productDto,HttpStatus.OK);
	}
	
	@GetMapping("/public/categories/{categoryId}/product")
	public ResponseEntity<ProductResponseDto> getProductsByCategory(@PathVariable Long categoryId){
		ProductResponseDto productResponseDto  = productService.getProductsByCategory(categoryId);
		return new ResponseEntity<ProductResponseDto>(productResponseDto,HttpStatus.OK);
	}

}
