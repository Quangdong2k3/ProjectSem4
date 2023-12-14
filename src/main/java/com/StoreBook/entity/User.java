package com.StoreBook.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;


import lombok.*;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fullname;
	@Column(unique = true)
	private String email;
	private String password;
	private String address;
	private String phone;

	@JsonManagedReference(value="cart-user")
	@OneToMany(mappedBy = "user")
	private List<Cart> cart = new ArrayList<>();

	@OneToOne(mappedBy = "user")
	@JsonManagedReference(value="ship-user")
	private ShippingDetail shippingdetail;

	@OneToMany(mappedBy = "user")
	@JsonManagedReference(value="review-user")
	List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "user2")
	@JsonManagedReference(value="reply-user")
	List<Review> reply = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	@JsonManagedReference(value="order-user")
	private List<Order> orders = new ArrayList<>();


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnoreProperties("users")
	private Set<Role> roles = new HashSet<Role>();
}