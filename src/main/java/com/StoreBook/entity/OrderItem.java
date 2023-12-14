package com.StoreBook.entity;

import javax.persistence.*;



import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Entity
@Table(name = "Order_items")
@Setter
@Getter
@NoArgsConstructor
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JsonBackReference(value="order-orderItem")
    @JoinColumn(name = "order_id")
    private Order order;

	private Integer quantity;
	private double price;

	@ManyToOne
	@JsonBackReference(value="book-orderItem")
	@JoinColumn(name = "book_id")
	private Book book;

}
