package com.shopify.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.model.Category;
import com.shopify.repo.CategoryRepo;
import com.shopify.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepo cateRepo;
	
	@Override
	public Category saveCategory(String categoryName,String photo) {
		Category cate = new Category();
		cate.setCategory(categoryName);
		cate.setPhoto(photo);
		Category category  = cateRepo.save(cate);
		return category;
	}

}
