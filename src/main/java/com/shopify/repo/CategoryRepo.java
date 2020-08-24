package com.shopify.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopify.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long>{
	
	Category findById(long id);

	@Query("SELECT COUNT(p) FROM Category p ")
	Long countCategory();
}
