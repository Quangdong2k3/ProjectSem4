package com.StoreBook.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private Date order_date;
    private int status;
    private double total_amount;
    private Long user_id;


    private String address;
    private String note; 
    private String phone;
}
