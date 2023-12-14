package com.StoreBook.entity;



import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Book_Images")
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonProperty("one")
	private String img1;
	@JsonProperty("two")
	private String img2;
	@JsonProperty("three")
	private String img3;
	@JsonProperty("four")
	private String img4;

	private String img5;

	@ManyToOne
	@JsonBackReference(value="book-img")
	@JoinColumn(name = "book_id")
	private Book book;
	
}
