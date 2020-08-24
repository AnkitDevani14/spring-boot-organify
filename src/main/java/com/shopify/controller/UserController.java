package com.shopify.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.shopify.model.*;
import com.shopify.repo.*;
import com.shopify.service.AddressService;
import com.shopify.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("http://localhost:4200")
public class UserController {
	
	@Autowired
	UserRepo userRepo;

	@Autowired
	CategoryRepo cateRepo;

	@Autowired
	ProductRepo productRepo;

	@Autowired
	CartService cartService;

	@Autowired
	private org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder BCryptPasswordEncoder;

	@Autowired
	CartRepo cartRepo;

	@Autowired
	AddressService addressService;

	@Autowired
    AddressRepo addressRepo;

	@Autowired
	SalesRepo salesRepo;


	@GetMapping("/")
	public ResponseEntity<?> getUser(){
		
		try {
			List<User> users = userRepo.findAll();
			return new ResponseEntity<>(users,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<?> findProducts(@PathVariable("id") Category id){
		List<Product> products = productRepo.products(id);

		return new ResponseEntity<>(products,HttpStatus.OK);
	}

	@GetMapping("/list-category")
	public ResponseEntity<?> getCategory(){

		try {

			return new ResponseEntity<>(cateRepo.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/add-cart")
	public ResponseEntity<?> addToCart(@RequestParam("user_id") String uid,
									   @RequestParam("product_id") String pid){

		Long userId = Long.parseLong(uid);
		Long pId = Long.parseLong(pid);

		Cart cart = cartService.saveCart(userId,pId);
		return new ResponseEntity(cart,HttpStatus.OK);
	}

	@GetMapping("/get-cart/{id}")
	public ResponseEntity<?> getCart(@PathVariable("id") Long id){
		User user = userRepo.findUserById(id);
		List<Cart> cart = cartRepo.listProductCart(user);


		return new ResponseEntity(cart,HttpStatus.OK);
	}

	@GetMapping("/get-cart-total/{id}")
	public ResponseEntity<?> getCartTotal(@PathVariable("id") Long id){
		User user = userRepo.findUserById(id);
		Double total = cartRepo.listProduct(user);



		return new ResponseEntity(total,HttpStatus.OK);
	}

	@GetMapping("/username/{username}")
	public ResponseEntity<?> getCart(@PathVariable("username") String username){
		Long id = userRepo.findUserId(username);
		return new ResponseEntity(id,HttpStatus.OK);
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

	@GetMapping("/count/{id}/{uid}")
	public ResponseEntity<?> countCart(@PathVariable("id") Product id,@PathVariable("uid") User uid){
		Long val = cartRepo.findProductById(id,uid);
		if(val == 1){
			return new ResponseEntity(true,HttpStatus.OK);
		}
		return new ResponseEntity(false,HttpStatus.OK);
	}

	@GetMapping("/count/{uid}")
	public ResponseEntity<?> countCart(@PathVariable("uid") User uid){
		Long val = cartRepo.findProduct(uid);

			return new ResponseEntity(val,HttpStatus.OK);
		
	}

	@DeleteMapping("delete/{id}/{uid}")
	public ResponseEntity<?> DeleteCart(@PathVariable("id") Product id,@PathVariable("uid") User uid){
		try {
			cartRepo.DeleteById(id,uid);

			return new ResponseEntity(true,HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity(e,HttpStatus.OK);
		}

	}

	@PostMapping("/add/{id}/{uid}")
	public ResponseEntity<?> AddQauntity(@PathVariable("id") Product id,@PathVariable("uid") User uid){

		try {
			Cart cart = cartRepo.findProduct(id,uid);
			Product product = cart.getProduct();
			Long quantity = product.getQuantity();
			Long currentQ = cart.getQuantity();
			if(currentQ < quantity)
			{
				cart.setQuantity(cart.getQuantity()+1);
				cart.setTotalPrice(cart.getTotalPrice()+cart.getPrice());
				cartRepo.save(cart);

				return new ResponseEntity(cart,HttpStatus.OK);
			}

				return new ResponseEntity(cart,HttpStatus.OK);


		}catch (Exception e){
			return new ResponseEntity(e,HttpStatus.OK);
		}
	}

	@PostMapping("/remove/{id}/{uid}")
	public ResponseEntity<?> removeQauntity(@PathVariable("id") Product id,@PathVariable("uid") User uid){

		try {
			Cart cart = cartRepo.findProduct(id,uid);
			if(cart.getQuantity() >= 2){
				cart.setQuantity(cart.getQuantity()-1);
				cart.setTotalPrice(cart.getTotalPrice()-cart.getPrice());
				cartRepo.save(cart);
			}


			return new ResponseEntity(cart,HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity(e,HttpStatus.OK);
		}
	}

	@PostMapping("/user-address")
	public ResponseEntity<?> saveAddress(@RequestBody HashMap<String, String> request){
		String address = request.get("address");
		String zipcode = request.get("zipcode");
		String city = request.get("city");
		String state = request.get("state");
		String user = request.get("user");

		User user1 = userRepo.findUserById(Long.parseLong(user));
		Long val = addressRepo.getAddressByUser(user1);
		if(val == 1){
			Address address2 = addressRepo.AddressByUser(user1);
			address2.setZipcode(zipcode);
			address2.setState(state);
			address2.setCity(city);
			address2.setAddress(address);
			addressRepo.save(address2);
			return new ResponseEntity<>(address2,HttpStatus.OK);
		}else {
			try {
				Address address1 = addressService.saveAddress(address, city, state, zipcode, user1);
				return new ResponseEntity<>(address1, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>("An Error ocured" + e, HttpStatus.BAD_REQUEST);
			}
		}

	}

	@GetMapping("/address/{uid}")
	public ResponseEntity<?> address(@PathVariable("uid") User uid){
		Address address = addressRepo.AddressByUser(uid);
		Address address1 = new Address(address.getId(),address.getAddress(),address.getCity(),address.getState(),address.getZipcode());
		return new ResponseEntity(address1,HttpStatus.OK);

	}

	@PostMapping("/add-sales/{uid}")
	public ResponseEntity<?> saveSales(@PathVariable("uid") User uid){
		List<Cart> cart = cartRepo.listProductCart(uid);
		for(Cart carts : cart){
			Product product = carts.getProduct();

			product.setQuantity(product.getQuantity() - carts.getQuantity());
			productRepo.save(product);


			Sales sale = new Sales();
			sale.setPrice(carts.getPrice());
			sale.setProduct(carts.getProduct());
			sale.setQuantity(carts.getQuantity());
			sale.setTotalPrice(carts.getTotalPrice());
			sale.setUser(carts.getUser());

			salesRepo.save(sale);
			cartRepo.delete(carts);

		}
		return new ResponseEntity<>(true,HttpStatus.OK);
	}



}
