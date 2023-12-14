package com.StoreBook.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

import lombok.*;


@Entity
@Table(name = "ShippingDetails")
@Getter
@Setter
@NoArgsConstructor
public class ShippingDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String address;
	private String phone;
	@Column(columnDefinition = "TEXT")
	private String moreInfo;


	@OneToOne
	@JsonBackReference(value="ship-user")
	@JoinColumn(name = "user_id")
	private User user;
}