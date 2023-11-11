package com.StoreBook.DTO;

import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class BookAuthor {
    @Min(value = 1,message = "Sách thêm vào vào không được để trống")
  private Long bookId; 
  @Min(value = 1,message = "Tác giả thêm vào vào không được để trống")
  private Long authorId; 
}
