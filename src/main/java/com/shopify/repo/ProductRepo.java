package com.shopify.repo;

import com.shopify.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopify.model.Product;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{

    @Query("SELECT p FROM Product p WHERE p.role =?1")
    List<Product> products(Category id);

    @Query("SELECT p FROM Product p WHERE p.id =?1")
    Product getProductByName(Long id);

    @Query("SELECT COUNT(p) FROM Product p ")
    Long countProduct();


}
