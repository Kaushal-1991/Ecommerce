package com.ecommerce.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	@GetMapping("/public/product/keyword/{keyword}")
	public ResponseEntity<ProductResponseDto> getProductsByCategory(@PathVariable String keyword){
		ProductResponseDto productResponseDto  = productService.getProductsByKeyword(keyword);
		return new ResponseEntity<ProductResponseDto>(productResponseDto,HttpStatus.OK);
	}
	
	@PutMapping("/admin/products/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
			                                       @PathVariable Long productId){
		
		ProductDto updateProduct = productService.updateProduct(productDto, productId);
		return new ResponseEntity<ProductDto>(updateProduct,HttpStatus.OK);
	}
	
	@DeleteMapping("/admin/products/{productId}")
	public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable Long productId){
		ProductResponseDto deleteProduct = productService.deleteProduct(productId);
		return new ResponseEntity<ProductResponseDto>(deleteProduct,HttpStatus.OK);
	}
	
	@PutMapping("/public/product/{productId}/image")
	public ResponseEntity<ProductDto> updateImage(@PathVariable Long productId,@RequestParam(name="image") MultipartFile image) throws IOException{
		ProductDto updateProductImage = productService.updateImage(productId,image);
		return new ResponseEntity<ProductDto>(updateProductImage,HttpStatus.OK);
	}

}
 