package com.StoreBook.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.*;

import lombok.*;


@Table(name = "CARTS")
@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// @Min(value =1,message="Sản phẩm thêm vào giỏ ít nhất là 1")
	private Integer quantity;
	private double price;

	@ManyToOne
	@JsonBackReference(value="cart-user")
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JsonBackReference(value="book-cart")
	@JoinColumn(name = "Book_id")
	private Book book;
}