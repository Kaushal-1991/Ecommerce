package com.ecommerce.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.ProductResponseDto;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repositories.CategoryRepository;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDto addProduct(Long categoryId, ProductDto productDto) {
		
		Category category = categoryRepository.findById(categoryId)
				                              .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
		
		Product product = modelMapper.map(productDto, Product.class);
		product.setCategory(category);
	    product.setImage("default.png");

	    double specialPrice = product.getPrice() -
	            ((product.getDiscount() * 0.01) * product.getPrice());
	    product.setSpecialPrice(specialPrice);

	    Product savedProduct = productRepository.save(product);;
		return modelMapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public ProductResponseDto getAllProduct() {
		List<Product> product = productRepository.findAll();
		
		List<ProductDto> productDto = product.stream()
				                             .map(p -> modelMapper.map(p, ProductDto.class))
				                             .collect(Collectors.toList());
		
		ProductResponseDto productResponseDto = new ProductResponseDto();
		productResponseDto.setContent(productDto);
		return productResponseDto;
	}

	@Override
	public ProductResponseDto getProductsByCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
		
		 List<Product> product= productRepository.findByCategoryOrderByPriceAsc(category);
		 
		 List<ProductDto> productList = product.stream()
				                               .map(p -> modelMapper.map(p, ProductDto.class))
				                               .collect(Collectors.toList());
		 
		 ProductResponseDto productResponseDto = new ProductResponseDto();
		 productResponseDto.setContent(productList);
		
		 return productResponseDto;
	}
}
