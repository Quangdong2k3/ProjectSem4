package com.StoreBook.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.Temporal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;


@Entity
@Table(name = "Books")
@Data

public class Book{
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	private String title;
	@Column(columnDefinition = "TEXT")
	private String description;
	
	private String image;
	
	private double price;
	
	@javax.persistence.Temporal(TemporalType.DATE)
	private Date publication_date;
	
	private Integer quantity;
	
	private double average_rating;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "BOOKS_Authors", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
	private Set<Author> authors = new HashSet<>();

	
	@JsonBackReference(value = "book-cate")
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@JsonBackReference(value ="book-publisher")
	@ManyToOne
	
	@JoinColumn(name = "publisher_id")
	private Publisher publisher;
	
	@JsonIgnore
	@OneToMany(mappedBy = "book")
	private List<Review> review = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "book")
	private List<OrderItem> orderItem = new ArrayList<>();
	
	@OneToMany(mappedBy = "book")
	@JsonBackReference(value="cart-book")
	private List<Cart> carts = new ArrayList<>();
	
	@OneToMany(mappedBy = "book")
	List<Image> img = new ArrayList<>();
	

}
