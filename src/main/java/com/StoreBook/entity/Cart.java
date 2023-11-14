package com.StoreBook.entity;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name = "CARTS")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// @Min(value =1,message="Sản phẩm thêm vào giỏ ít nhất là 1")
	private Integer quantity;
	private double price;

	@ManyToOne	
	@JoinColumn(name = "user_id")
	@JsonManagedReference
	private User user;
	
	
	
	
	@ManyToOne

	@JsonManagedReference(value = "cart-book")
	@JoinColumn(name = "Book_id")
	private Book book;
}