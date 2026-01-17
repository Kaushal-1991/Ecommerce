package com.ecommerce.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.ProductResponseDto;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repositories.CategoryRepository;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.service.FileService;
import com.ecommerce.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FileService  fileService;
	
	@Value("${product.image}")
	private String path; 

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

	@Override
	public ProductDto updateProduct(ProductDto productDto, Long productId) {
		Product product = productRepository.findById(productId)
				                           .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));
		
		Product updatedProduct = modelMapper.map(productDto, Product.class);
		product.setProductName(updatedProduct.getProductName());
		product.setDescription(updatedProduct.getDescription());
		product.setDiscount(updatedProduct.getDiscount());
		product.setPrice(updatedProduct.getPrice());
		product.setQuantity(updatedProduct.getQuantity());
		product.setSpecialPrice(updatedProduct.getSpecialPrice());
		
		productRepository.save(product);
		
		return modelMapper.map(updatedProduct, ProductDto.class);
	}

	@Override
	public ProductResponseDto deleteProduct(Long productId) {
		Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));
		
		productRepository.delete(product);
		return modelMapper.map(product, ProductResponseDto.class);
	}

	@Override
	public ProductResponseDto getProductsByKeyword(String keyword) {
		List<Product> product = productRepository.findByProductNameLikeIgnoreCase("%"+ keyword + "%");
		List<ProductDto> savedProduct = product.stream().map(p -> modelMapper.map(p, ProductDto.class)).collect(Collectors.toList());
		ProductResponseDto responseDto = new ProductResponseDto();
		responseDto.setContent(savedProduct);
		return responseDto;
	}

	@Override
	public ProductDto updateImage(Long productId, MultipartFile image) throws IOException {
		Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

		String fileName = fileService.uploadImage(path, image);
		productFromDb.setImage(fileName);
		Product product = productRepository.save(productFromDb);
		
		return modelMapper.map(product, ProductDto.class);
	}

	
}
