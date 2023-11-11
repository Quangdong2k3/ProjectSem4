package com.StoreBook.entity;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Data
@Table(name = "Book_Images")
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonProperty("one")
	private String imgone;
	@JsonProperty("two")
	private String imgtwo;
	@JsonProperty("three")
	private String imgthree;
	@JsonProperty("four")
	private String imgfour;

	private String imgfive;
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
}
