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

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "BOOKS_Authors", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
	private Set<Author> authors = new HashSet<>();

	@JsonBackReference(value = "book-cate")
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@JsonBackReference(value = "book-publisher")
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
	@JsonBackReference(value = "cart-book")
	private List<Cart> carts = new ArrayList<>();

	@OneToMany(mappedBy = "book")
	List<Image> img = new ArrayList<>();

}
