package com.shopify.repo;

import com.shopify.model.Address;
import com.shopify.model.Product;
import com.shopify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

    @Query("SELECT COUNT(p) FROM Address p WHERE p.user =?1")
    Long getAddressByUser(User id);

    @Query("SELECT p FROM Address p WHERE p.user =?1")
    Address AddressByUser(User id);
}
