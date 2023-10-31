package com.tripstore.cartitemsmicroservice.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private String productImageUrl;
    private Double productRating;
    private String productCategory;
}
