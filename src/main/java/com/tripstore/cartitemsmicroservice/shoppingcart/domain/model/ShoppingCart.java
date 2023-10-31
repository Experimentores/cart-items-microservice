package com.tripstore.cartitemsmicroservice.shoppingcart.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
    private Long id;
    private LocalDateTime cartDateCreated;
    private String cartStatus;
}
