package com.StoreBook.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShippingDTO {

	private Long s_id;

	private String s_address;
	private String s_phone;
	
	private String s_moreInfo;
	
	private Long s_user_id;
}
