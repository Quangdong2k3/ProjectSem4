package com.StoreBook.DTO;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


import lombok.Data;

@Data

public class UserDTO {
    private Long id;

    @NotBlank(message="Please enter a user name")
    private String fullname;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email should be valid")
    // @Email(message="Please enter a Email valid")
    private String email;

    @NotBlank(message="Please enter a password")
    private String password;

    @NotBlank(message="Please enter a password")
    private String confirm_password;

    @Min(value=1, message="Please select role")
    private Long role_id;
    
    @AssertTrue(message = "Bạn phải đồng ý với điều khoản")
    private boolean terms;

    private String address;
	private String phone;
    
}
