package com.StoreBook.DTO;


import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BookDTO {
	private Long id;

	@NotBlank(message="Tên sách không được trống")
	private String title;
	@NotBlank(message="Mô tả không được trống")
	private String description;
	
	@NotEmpty(message="Ảnh không được trống")
	// @Pattern(regexp = ".*\\.(jpg|png)$", message = "Only .jpg and .png files are allowed")
	private MultipartFile[] files;
	private String image;
	@Min(value = 1, message = "Giá sách không được trống!")
	private double price;
	
	@NotBlank(message = "Ngày xuất bản không được để trống")
	private String publication_date;
//	private Date publication_date1;
	@Min(value =1, message = "Số lượng phải lớn hơn 1")
	private int quantity;
	

	private double average_rating;
	
	@Min(value =1, message = "Thể loại không được trống!")
	private Long category_id;
	
	@Min(value =1, message = "Nhà xuất bản không được trống!")
	private Long publisher_id;


}
