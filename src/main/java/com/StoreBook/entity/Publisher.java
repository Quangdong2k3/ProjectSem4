package com.StoreBook.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Entity
@Table(name = "publishers")
@Data
public class Publisher{
	
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;


	private String name;


	private String location;

	private String website;

	
	@JsonManagedReference(value ="book-publisher")
	@OneToMany(mappedBy = "publisher")
	List<Book> books = new ArrayList<>();
}
