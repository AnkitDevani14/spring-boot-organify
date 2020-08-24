package com.shopify.serviceImpl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.model.Category;
import com.shopify.model.Product;
import com.shopify.repo.CategoryRepo;
import com.shopify.repo.ProductRepo;
import com.shopify.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	ProductRepo prodRepo;
	
	@Autowired
	CategoryRepo cateRepo;

	@Override
	public Product saveProduct(String productName, double price, long quantity, String description, Category role,String photo) {
		Product product = new Product();
		product.setProductName(productName);
		product.setPrice(price);
		product.setQuantity(quantity);
		product.setDescription(description);
		product.setPhoto(photo);
		
		
		product.setRole(role);
		
		Product prod = prodRepo.save(product);
		
		return prod;
	}



}
