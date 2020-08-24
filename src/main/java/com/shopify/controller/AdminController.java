package com.shopify.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.shopify.repo.SalesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.shopify.model.Category;
import com.shopify.model.Product;
import com.shopify.model.User;
import com.shopify.repo.CategoryRepo;
import com.shopify.repo.ProductRepo;
import com.shopify.repo.UserRepo;
import com.shopify.service.CategoryService;
import com.shopify.service.ProductService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CategoryService cateService;

	@Autowired
	private org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder BCryptPasswordEncoder;

	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CategoryRepo cateRepo;
	
	@Autowired
	ProductService prodService;

	@Autowired
	SalesRepo salesRepo;
	
	@GetMapping("/list-category")
	public ResponseEntity<?> getCategory(){
		
		try {
			
			return new ResponseEntity<>(cateRepo.findAll(),HttpStatus.OK);
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
	
	@PostMapping("/add-category")
	public ResponseEntity<?> saveCategory(@RequestParam("file") MultipartFile image,@RequestParam("categoryName") String categoryName){
		
		try {

			
			
			String folder = "E:\\Learning Material\\PROJECT\\shopify\\src\\assets\\categories\\";
			String dbfolder = "assets\\categories\\";
			byte bytes[] = image.getBytes();
			Path path = Paths.get(folder + image.getOriginalFilename());
			Path dbpath = Paths.get(dbfolder + image.getOriginalFilename());
			Files.write(path,bytes);
			System.out.println(path);
			Category category =  cateService.saveCategory(categoryName,dbpath.toString());
			return new ResponseEntity<>(category, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Some Error occured: "+ e, HttpStatus.BAD_REQUEST);
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

	@RequestMapping(value = "delete-product/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		 	try{
				productRepo.deleteById(id);

				return new ResponseEntity("ok",HttpStatus.OK);
			}catch(Exception e){
				return new ResponseEntity(e,HttpStatus.BAD_REQUEST);
			}


	}

	@RequestMapping(value = "delete-category/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletecategory(@PathVariable("id") Long id) {
		cateRepo.deleteById(id);

		return new ResponseEntity("ok",HttpStatus.OK);

	}

	@PostMapping("/add-product")
	public ResponseEntity<?> saveProduct(@RequestParam("file") MultipartFile image,@RequestParam("productName") String productName,
			@RequestParam("price") double price,
			@RequestParam("quantity") long quantity,
			@RequestParam("description") String description,
			@RequestParam("cate") int cate
			){
		
		try {

			Category category = cateRepo.findById(cate);
			
			String folder = "E:\\Learning Material\\PROJECT\\shopify\\src\\assets\\products\\";
			String dbfolder = "assets\\products\\";
			byte bytes[] = image.getBytes();
			Path path = Paths.get(folder + image.getOriginalFilename());
			Path dbpath = Paths.get(dbfolder + image.getOriginalFilename());
			Files.write(path,bytes);
			
			Product product = prodService.saveProduct(productName,price,quantity, description, category, dbpath.toString());
			
			return new ResponseEntity<>(product, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.OK);
		}
			
		
	}


	@PostMapping("/change-password")
	public ResponseEntity<?> saveProduct(@RequestParam("password") String password,@RequestParam("uid") User user){
		try {


			String encryptedPassword = BCryptPasswordEncoder.encode(password);
			user.setPassword(encryptedPassword);
			userRepo.save(user);
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		catch (Exception e){
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}


	@PostMapping("/update-profile")
    public ResponseEntity<?> update(@RequestBody HashMap<String, String> request){
	    String id = request.get("id");
        String username = request.get("username");
        String email = request.get("email");
        String firstname = request.get("firstname");
        String lastname = request.get("lastname");

        try {
            User user = userRepo.findUserById(Long.parseLong(id));
            user.setEmail(email);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setUsername(username);
            User users = userRepo.save(user);
            return new ResponseEntity<>(users,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An Error ocured"+e,HttpStatus.BAD_REQUEST);
        }

    }

	@PostMapping("/update-product")
	public ResponseEntity<?> saveProduct(@RequestParam("id") Long id,@RequestParam("productName") String productName,
										 @RequestParam("price") double price,
										 @RequestParam("quantity") long quantity,
										 @RequestParam("description") String description
	){


			Product product =productRepo.getProductByName(id);

			product.setProductName(productName);
			product.setQuantity(quantity);
			product.setPrice(price);
			product.setDescription(description);
			productRepo.save(product);

			return new ResponseEntity<>(product, HttpStatus.OK);


	}

	@GetMapping("/get-product/{id}")
	public ResponseEntity<Void> EditProduct(@PathVariable("id") Long id) {

			Product product = productRepo.getProductByName(id);
			return new ResponseEntity(product,HttpStatus.OK);
		}

    @GetMapping("/get-profile")
    public ResponseEntity<Void> GetProfile() {

        Optional<User> user = userRepo.findByUsername("admin");
        return new ResponseEntity(user,HttpStatus.OK);
    }

    @GetMapping("/count-user")
	public ResponseEntity<?> countUser(){
		Long val = userRepo.countUser();
		return new ResponseEntity(val, HttpStatus.OK);
	}

	@GetMapping("/count-category")
	public ResponseEntity<?> countCategory(){
		Long val = cateRepo.countCategory();
		return new ResponseEntity(val, HttpStatus.OK);
	}

	@GetMapping("/count-product")
	public ResponseEntity<?> countProduct(){
		Long val = productRepo.countProduct();
		return new ResponseEntity(val, HttpStatus.OK);
	}

	@GetMapping("/count-sales")
	public ResponseEntity<?> countSales(){
		Long val = salesRepo.countUser();
		return new ResponseEntity(val, HttpStatus.OK);
	}

	@GetMapping("list-user")
	public ResponseEntity<?> getUser(@RequestParam(defaultValue = "0") int page){
		Page<User> user=  userRepo.findAll(PageRequest.of(page,4));
		return  new ResponseEntity(user , HttpStatus.OK);
	}
	
}	
