package com.shopify.service;

import com.shopify.model.User;

public interface UserService {
	
	public User saveUser(String username, String email,String password, String firstname, String lastname);
	
	public User saveAdmin(String username, String email,String password, String firstname, String lastname);
	
	
}
