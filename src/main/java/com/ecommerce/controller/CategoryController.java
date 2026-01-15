package com.ecommerce.controller;

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

import com.ecommerce.config.AppConstants;
import com.ecommerce.dto.CategoryRequestDto;
import com.ecommerce.dto.CategoryResponseDto;
import com.ecommerce.entity.Category;
import com.ecommerce.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class CategoryController {

	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("/public/categories")
	public ResponseEntity<CategoryResponseDto> getCategories(@RequestParam(name="pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
			@RequestParam(name="pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
			@RequestParam(name="sortBy" , defaultValue = AppConstants.SORT_BY , required = false) String sortBy,
			@RequestParam(name="sortOrder" , defaultValue = AppConstants.SORT_DIR , required = false) String sortOrder){
		CategoryResponseDto list= categoryService.findAllCategory(pageNumber,pageSize,sortBy,sortOrder);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/public/categories/{categoryId}")
	public ResponseEntity<CategoryRequestDto> getCategory(@PathVariable Long categoryId){
		CategoryRequestDto category = categoryService.findCategory(categoryId);
		return new ResponseEntity<>(category,HttpStatus.OK);
	}
	
	@PostMapping("/public/categories")
	public ResponseEntity<CategoryRequestDto> createCategory(@Valid @RequestBody CategoryRequestDto category){
		CategoryRequestDto categoryRequestDto = categoryService.createCategory(category);
		return new ResponseEntity<>(categoryRequestDto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/admin/categories/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
		String status  =  categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(status , HttpStatus.OK );
	}
	
	@PutMapping("/public/categories/{categoryId}")
	public ResponseEntity<CategoryRequestDto> UpdateCategory(@RequestBody Category category,@PathVariable Long categoryId){
		CategoryRequestDto savedCategory = categoryService.updateCategory(category,categoryId);
		return new ResponseEntity<>(savedCategory , HttpStatus.OK );
	}
}
