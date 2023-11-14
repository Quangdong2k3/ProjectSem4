package com.StoreBook.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

	@OneToMany(mappedBy = "user")
	@JsonBackReference(value = "user-cart")
	private List<Cart> cart = new ArrayList<>();

	@OneToOne(mappedBy = "user")
	private ShippingDetail shippingdetail;

	@OneToMany(mappedBy = "user")
	List<Review> reviews = new ArrayList<>();

	@OneToOne(mappedBy = "user")
	private Order order;
	
    @JsonManagedReference(value = "user-roles")
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<Role>();
}