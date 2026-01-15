package com.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByCategoryName(String categoryName);

}
