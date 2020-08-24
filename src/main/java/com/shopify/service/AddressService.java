package com.shopify.service;

import com.shopify.model.Address;
import com.shopify.model.User;

public interface AddressService {

   Address saveAddress(String address, String city, String state, String zipcode, User user);
}
