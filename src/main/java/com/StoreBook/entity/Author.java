package com.StoreBook.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "AUTHORS")
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String biography;

	
	@ManyToMany(mappedBy = "authors")
	private Set<Book> books = new HashSet<>();
}