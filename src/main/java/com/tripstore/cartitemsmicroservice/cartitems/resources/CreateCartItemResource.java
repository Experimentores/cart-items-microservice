package com.tripstore.cartitemsmicroservice.cartitems.resources;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCartItemResource {
    @NotNull
    @Positive(message = "Must be a valid quantity")
    private int quantity;
    @NotNull
    @Positive(message = "Must be a valid subtotal price")
    private double subtotal;
    @NotNull
    @Positive(message = "Must be a valid shopping cart id")
    private Long shoppingCartId;

    @NotNull
    @Positive(message = "Must be a valid product id")
    private Long productId;
}
