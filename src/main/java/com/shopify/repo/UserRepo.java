package com.shopify.repo;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopify.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	
	Optional<User> findByUsername(String userName);


	@Query("SELECT p FROM User p WHERE p.id =?1")
	User findUserById(Long id);

	@Query("SELECT p.id FROM User p WHERE p.username =?1")
	Long findUserId(String username);


	@Query("SELECT COUNT(p) FROM User p ")
	Long countUser();


	
}
