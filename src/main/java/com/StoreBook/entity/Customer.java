package com.StoreBook.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	private String address;
	private String phone;

	@OneToMany(mappedBy = "customer")
	@JsonBackReference(value = "customer-cart")
	private List<Cart> cart = new ArrayList<>();

	@OneToOne(mappedBy = "customer")
	private ShippingDetail shippingdetail;

	@OneToMany(mappedBy = "customer")
	List<Review> reviews = new ArrayList<>();

	@OneToOne(mappedBy = "customer")
	private Order order;
}