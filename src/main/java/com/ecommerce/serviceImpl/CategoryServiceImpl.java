package com.ecommerce.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.CategoryRequestDto;
import com.ecommerce.dto.CategoryResponseDto;
import com.ecommerce.entity.Category;
import com.ecommerce.exception.APIException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repositories.CategoryRepository;
import com.ecommerce.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryResponseDto findAllCategory(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder) {
		
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        		
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		Page<Category> catgoryPage = categoryRepository.findAll(pageable);

		List<Category> catgories = catgoryPage.getContent();
		
		if (catgories.isEmpty()) {
			throw new APIException("Category is empty !!!");
		}
		
		List<CategoryRequestDto> requestDtos = catgories.stream()
				                                        .map(cat -> modelMapper.map(cat, CategoryRequestDto.class))
				                                        .toList();
		
		CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
		categoryResponseDto.setContent(requestDtos);
		categoryResponseDto.setPageNumber(catgoryPage.getNumber());
		categoryResponseDto.setPageSize(catgoryPage.getSize());
		categoryResponseDto.setTotalElements(catgoryPage.getTotalElements());
		categoryResponseDto.setTotalPages(catgoryPage.getTotalPages());
		
		return categoryResponseDto;
	}

	@Override
	public CategoryRequestDto createCategory(CategoryRequestDto category) {
		Category cat = categoryRepository.findByCategoryName(category.getCategoryName());
		if (cat != null) {
			throw new APIException("Category with " + category.getCategoryName() + " already exists");
		}
		Category categorySaved = modelMapper.map(category, Category.class);
		Category categorySavedEntity= categoryRepository.save(categorySaved);
		
		CategoryRequestDto categoryRequestDto =  modelMapper.map(categorySavedEntity, CategoryRequestDto.class);
		
		return categoryRequestDto;
	}

	@Override
	public String deleteCategory(Long categoryId) {
		List<Category> categories = categoryRepository.findAll();
		Category categ = categories.stream().filter(e -> e.getCategoryId().equals(categoryId)).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		categoryRepository.delete(categ);
		return "Category Deleted Successfully !!!";
	}

	@Override
	public CategoryRequestDto updateCategory(Category category, Long categoryId) {
		List<Category> categories = categoryRepository.findAll();
		Optional<Category> findFirst = categories.stream().filter(e -> e.getCategoryId().equals(categoryId))
				.findFirst();
		if (findFirst.isPresent()) {
			Category exitingCategory = findFirst.get();
			exitingCategory.setCategoryName(category.getCategoryName());
			Category savedCategory = categoryRepository.save(exitingCategory);
			CategoryRequestDto categoryRequestDto =  modelMapper.map(savedCategory, CategoryRequestDto.class);
			return categoryRequestDto;
		} else {
			throw new ResourceNotFoundException("Category", "CategoryId", categoryId);
		}
	}

	@Override
	public CategoryRequestDto findCategory(Long categoryId) {
		
		Optional<Category> category = categoryRepository.findById(categoryId);
		if(category.isPresent()) {
			Category cat = category.get();
			CategoryRequestDto categoryRequestDto = modelMapper.map(cat, CategoryRequestDto.class);
			return categoryRequestDto;
		}else {
			throw new ResourceNotFoundException("Cateogry","cateogryid",categoryId);
		}		
	}



}
