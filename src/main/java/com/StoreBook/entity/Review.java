package com.StoreBook.entity;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer rating;
	private String comment;
	private Integer status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference(value = "review-user")
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference(value = "book-review")
	@JoinColumn(name = "book_id")
	private Book book;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference(value = "reply-user")
	@JoinColumn(name = "admin_id")
	private User user2;
	
	private String reply;

@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date update_at;
}
