package com.shopify.service;

import com.shopify.model.Category;
import com.shopify.model.Product;

public interface ProductService {
	
	public Product saveProduct(String productName,double price,long quantity,String description,Category role,String photo);




}
