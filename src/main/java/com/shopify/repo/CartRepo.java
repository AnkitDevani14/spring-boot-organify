package com.shopify.repo;

import com.shopify.model.Cart;
import com.shopify.model.Invoice;
import com.shopify.model.Product;
import com.shopify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

    @Query("SELECT SUM(m.totalPrice) FROM Cart m WHERE m.user =?1")
    public Double listProduct(User uid);

    @Query("SELECT m FROM Cart m WHERE m.user =?1")
    public List<Cart> listProductCart(User uid);

    @Query("SELECT COUNT(p) FROM Cart p WHERE p.product =?1 and p.user =?2")
    Long findProductById(Product id,User uid);

    @Query("SELECT COUNT(p) FROM Cart p WHERE  p.user =?1")
    Long findProduct(User uid);

    @Query("SELECT p FROM Cart p WHERE p.product =?1 and p.user =?2")
    Cart findProduct(Product id,User uid);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cart p WHERE p.product =?1 and p.user =?2")
    void DeleteById(Product id,User uid);



    @Query("SELECT new com.shopify.model.Invoice((SELECT city FROM Address WHERE user = u.id),(SELECT state FROM Address WHERE user = u.id),(SELECT zipcode FROM Address WHERE user = u.id),(SELECT address FROM Address WHERE user = u.id),u.firstname, u.lastname, c.price, c.quantity, c.totalPrice ,(SELECT productName FROM Product WHERE id = c.product))   FROM Cart c join User u on u.id = c.user where user_id =?1")
     List<Invoice> join(Long id);



}

