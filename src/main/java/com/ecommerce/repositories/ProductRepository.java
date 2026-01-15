package com.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByCategoryOrderByPriceAsc(Category category);

}
