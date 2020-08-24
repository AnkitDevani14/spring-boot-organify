package com.shopify.service;

import com.shopify.model.Cart;
import com.shopify.model.Product;
import com.shopify.model.User;

public interface CartService {

    Cart saveCart(Long uid, Long pid);
}
