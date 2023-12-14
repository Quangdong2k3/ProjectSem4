package com.StoreBook.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WishList {
    // @JsonIgnore
    private long id;

    // @JsonProperty
    private long b_id;

    // @JsonProperty
    private double w_price;

    // @JsonProperty
    private int quantity;
    // @JsonIgnore
    private BookDTO book;
}
