package com.StoreBook.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CartDTO {
   private Long id;
   private double price;
   private int quantity;
   private Long book_id;
   private  BookDTO book;
   private  Long cus_id;
   private CustomerDTO customer;
}
