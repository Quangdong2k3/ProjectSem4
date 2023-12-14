package com.StoreBook.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Entity
@Table(name = "Books")
@Getter
@Setter
@NoArgsConstructor

public class Book {
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

	@ManyToMany
	@JoinTable(name = "BOOKS_Authors", joinColumns = @JoinColumn(name = "book_id"), 
	inverseJoinColumns = @JoinColumn(name = "author_id"))
	@JsonIgnoreProperties("books")
	private Set<Author> authors;

	@ManyToOne
	@JsonBackReference(value="book-category")
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JsonBackReference(value="book-publisher")
	@JoinColumn(name = "publisher_id")
	private Publisher publisher;


	@OneToMany(mappedBy = "book")
	@JsonManagedReference(value="book-review")
	private List<Review> review = new ArrayList<>();

	@OneToMany(mappedBy = "book")
	@JsonManagedReference(value="book-orderItem")
	private List<OrderItem> orderItem = new ArrayList<>();

	@OneToMany(mappedBy = "book")
	@JsonManagedReference(value="book-cart")
	private List<Cart> carts = new ArrayList<>();


	@OneToMany(mappedBy = "book")
	@JsonManagedReference(value="book-img")
	List<Image> img = new ArrayList<>();

}
