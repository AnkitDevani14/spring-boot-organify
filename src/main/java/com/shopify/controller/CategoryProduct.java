package com.shopify.controller;

import com.shopify.model.Category;
import com.shopify.model.Product;
import com.shopify.repo.CategoryRepo;
import com.shopify.repo.ProductRepo;
import com.shopify.repo.UserRepo;
import com.shopify.service.CategoryService;
import com.shopify.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categpory-product")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryProduct {
    @Autowired
    UserRepo userRepo;

    @Autowired
    CategoryService cateService;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CategoryRepo cateRepo;

    @Autowired
    ProductService prodService;

    @GetMapping("/list-category")
    public ResponseEntity<?> getCategory(){

        try {

            return new ResponseEntity<>(cateRepo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/list-products")
    public ResponseEntity<?> getProducts(){

        try {

            return new ResponseEntity<>(productRepo.findAll(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> findCategory(@PathVariable("id") long id){
        Category cate = cateRepo.findById(id);

        return new ResponseEntity<>(cate,HttpStatus.OK);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findProducts(@PathVariable("id") Category id){
        List<Product> products = productRepo.products(id);

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/get-product/{id}")
    public ResponseEntity<Void> EditProduct(@PathVariable("id") Long id) {

        Product product = productRepo.getProductByName(id);
        return new ResponseEntity(product,HttpStatus.OK);
    }


}
