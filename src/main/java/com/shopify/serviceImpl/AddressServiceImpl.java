package com.shopify.serviceImpl;

import com.shopify.repo.AddressRepo;
import com.shopify.model.Address;
import com.shopify.model.User;
import com.shopify.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepo addressRepo;

    @Override
    public Address saveAddress(String address, String city, String state, String zipcode, User user) {
       Address address1 = new Address();
       address1.setAddress(address);
       address1.setCity(city);
       address1.setState(state);
       address1.setZipcode(zipcode);
       address1.setUser(user);
       Address address2 = addressRepo.save(address1);
       return  address2;
    }
}
