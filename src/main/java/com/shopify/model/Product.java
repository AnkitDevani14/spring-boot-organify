package com.shopify.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity

public class Product implements Serializable{
	private static final long serialVersionUID = 6643312337390101416L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String productName;
	private double price;
	private long quantity;
	private String description;
	private String photo;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catogory_id", referencedColumnName = "id")
    private Category role;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(String productName, double price, long quantity, String description, Category role,String photo) {
		
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.role = role;
		this.photo = photo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public long getId() {
		return id;
	}

	public String getProductName() {
		return productName;
	}

	public double getPrice() {
		return price;
	}

	public long getQuantity() {
		return quantity;
	}

	public String getDescription() {
		return description;
	}

	public Category getRole() {
		return role;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRole(Category role) {
		this.role = role;
	}
	
	
}
