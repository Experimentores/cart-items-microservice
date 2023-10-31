package com.tripstore.cartitemsmicroservice.cartitems.resources;

import com.tripstore.cartitemsmicroservice.products.domain.model.Product;
import com.tripstore.cartitemsmicroservice.shoppingcart.domain.model.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data()
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResource {
    private Long id;
    private int quantity;
    private double subtotal;
    private ShoppingCart shoppingCart;
    private Product product;
}
