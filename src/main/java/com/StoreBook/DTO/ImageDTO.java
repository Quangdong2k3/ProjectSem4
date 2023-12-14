package com.StoreBook.DTO;


import javax.validation.constraints.Min;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ImageDTO {
	int id;
	@Size(min = 2,max=5,message = "Bạn phải chọn ít nhất môtj file")
	MultipartFile[] files;
	
	@NotNull(message = "Không được để trống")
	@Min(value = 1)
	private Long bookid;
}
