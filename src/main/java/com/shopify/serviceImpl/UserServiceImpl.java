package com.shopify.serviceImpl;

import javax.transaction.Transactional;

import org.aspectj.apache.bcel.classfile.Module.Uses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.shopify.model.User;
import com.shopify.repo.UserRepo;
import com.shopify.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder BCryptPasswordEncoder; 
	
	@Override
	public User saveUser(String username, String email, String password, String firstname, String lastname
			) {
		String encryptedPassword = BCryptPasswordEncoder.encode(password);
		
		User users = new User();
		users.setUsername(username);
		users.setEmail(email);
		users.setLastname(lastname);
		users.setFirstname(firstname);
		users.setPassword(encryptedPassword);
		users.setRoles("ROLE_USER");
		users.setEnabled(true);
		userRepo.save(users);
		return users;
	}

	@Override
	public User saveAdmin(String username, String email, String password, String firstname, String lastname) {
String encryptedPassword = BCryptPasswordEncoder.encode(password);
		
		User users = new User();
		users.setUsername(username);
		users.setEmail(email);
		users.setLastname(lastname);
		users.setFirstname(firstname);
		users.setPassword(encryptedPassword);
		users.setRoles("ROLE_ADMIN");
		users.setEnabled(true);
		userRepo.save(users);
		return users;
	}

}
