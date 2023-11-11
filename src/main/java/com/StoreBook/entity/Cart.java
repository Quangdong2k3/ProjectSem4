package com.StoreBook.entity;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CARTS")
@Getter
@Setter
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// @Min(value =1,message="Sản phẩm thêm vào giỏ ít nhất là 1")
	private Integer quantity;
	private double price;

	@ManyToOne	
	@JoinColumn(name = "customer_id")
	@JsonManagedReference
	private Customer customer;
	
	
	
	
	@ManyToOne

	@JsonManagedReference(value = "cart-book")
	@JoinColumn(name = "Book_id")
	private Book book;
}