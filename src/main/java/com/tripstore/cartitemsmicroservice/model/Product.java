package com.tripstore.cartitemsmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String productName;
    private String productDescription;
    private Double productPrice;
    private String productImageUrl;
    private Double productRating;
    private String productCategory;
}
