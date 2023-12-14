package com.StoreBook.DTO;

import java.util.Date;

import com.StoreBook.entity.Book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDTO {
    private Long id;

    private Integer rating;
    private String comment;
    private Integer status;

    private UserDTO user;
    private Long user_id;

    private BookDTO book;
    private Long book_id;

    private UserDTO admin;
    private Long admin_id;
    private String reply;

    private Date created_at;
    private Date updated_at;

}
