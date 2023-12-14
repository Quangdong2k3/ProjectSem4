package com.StoreBook.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

import lombok.*;
import lombok.Setter;

@Entity
@Table(name = "Orders")
@Setter
@Getter
@NoArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "order")
	@JsonManagedReference(value="order-orderItem")
	private List<OrderItem> orderItem = new ArrayList<>();
	private Date order_date;
	private double total_amount;
	private Integer status;

	@ManyToOne
	@JsonBackReference(value="order-user")
	@JoinColumn(name = "user_id")
	private User user;

}