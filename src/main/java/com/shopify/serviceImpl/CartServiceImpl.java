package com.shopify.serviceImpl;

import com.shopify.model.Cart;
import com.shopify.model.Product;
import com.shopify.model.User;
import com.shopify.repo.CartRepo;
import com.shopify.repo.ProductRepo;
import com.shopify.repo.UserRepo;
import com.shopify.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CartRepo cartRepo;

    @Override
    public Cart saveCart(Long uid, Long pid) {
        User user = userRepo.findUserById(uid);
        Product product = productRepo.getProductByName(pid);
        Cart cart = new Cart();

        double total_price = product.getPrice() * 1;

        cart.setQuantity(1);
        cart.setTotalPrice(total_price);
        cart.setUser(user);
        cart.setPrice(product.getPrice());
        cart.setProduct(product);
        return cartRepo.save(cart);
    }


}
