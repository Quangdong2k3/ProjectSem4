package com.StoreBook.DTO;

import com.StoreBook.entity.Book;
import com.StoreBook.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class CartDTO {
   private Long id;
   private double price;
   private int quantity;
   private Long book_id;
   private Book book;
   private Long cus_id;
   private CustomerDTO customer;
   private User user;

}
