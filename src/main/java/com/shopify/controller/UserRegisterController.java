package com.shopify.controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shopify.model.Invoice;
import com.shopify.model.Sales;
import com.shopify.model.SalesReport;
import com.shopify.repo.CartRepo;
import com.shopify.repo.SalesRepo;
import com.shopify.repo.UserRepo;
import com.shopify.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.shopify.model.User;
import com.shopify.service.UserService;
import com.shopify.serviceImpl.UserServiceImpl;



@RestController
@RequestMapping("/api/register")
@CrossOrigin(origins = "http://localhost:4200")
public class UserRegisterController {
		
	@Autowired
	UserService userService;

	@Autowired
	UserRepo userRepo;

	@Autowired
	private ReportService service;

	@Autowired
	private org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder BCryptPasswordEncoder;

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	SalesRepo salesRepo;


	
	@PostMapping("/user")
	public ResponseEntity<?> saveUser(@RequestBody HashMap<String, String> request){
		String username = request.get("username");
		String email = request.get("email");
		String password = request.get("password");
		String firstname = request.get("firstname");
		String lastname = request.get("lastname");
		
		try {
			User users = userService.saveUser(username, email, password, firstname, lastname);
			return new ResponseEntity<>(users,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An Error ocured"+e,HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping("list-sales/{month}/{year}")
	public ResponseEntity<?> sales(@PathVariable("month") int month,@PathVariable("year") int year){
		List<SalesReport> sales = salesRepo.salesReport(month,year);
		return new ResponseEntity(sales,HttpStatus.OK);
	}

	@GetMapping("/check-pwd/{uid}/{pwd}")
	public ResponseEntity<?> checPwd(@PathVariable Long uid,@PathVariable String pwd){
		User user = userRepo.findUserById(uid);
		String pwd1 = user.getPassword();



		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		boolean isPasswordMatch = passwordEncoder.matches(pwd, pwd1);

		return new ResponseEntity(isPasswordMatch,HttpStatus.OK);

	}

	@GetMapping("/username/{username}")
	public ResponseEntity<?> getCart(@PathVariable("username") String username){
		Long id = userRepo.findUserId(username);
		return new ResponseEntity(id,HttpStatus.OK);
	}



	
	@PostMapping("/admin")
	public ResponseEntity<?> saveAdmin(@RequestBody HashMap<String, String> request){
		String username = request.get("username");
		String email = request.get("email");
		String password = request.get("password");
		String firstname = request.get("firstname");
		String lastname = request.get("lastname");
		
		try {
			User users = userService.saveAdmin(username, email, password, firstname, lastname);
			return new ResponseEntity<>(users,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An Error ocured"+e,HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping("/report/{id}")
	public ResponseEntity<?> generateReport(@PathVariable Long id) throws FileNotFoundException, JRException {
		String path = service.exportReport("pdf",id);
		System.out.println(path);
		Map<String, String> responseMap = new HashMap();
		responseMap.put("path", path);

		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getInvoice(@PathVariable Long id) throws FileNotFoundException, JRException {
		List<Invoice> invoice = cartRepo.join(id);
		return new ResponseEntity<>(invoice,HttpStatus.OK);
	}
}
