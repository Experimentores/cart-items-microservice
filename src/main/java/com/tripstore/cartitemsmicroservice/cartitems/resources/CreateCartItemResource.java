package com.tripstore.cartitemsmicroservice.cartitems.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateCartItemResource {
    @NotBlank
    @NotNull
    private int productQuantity;
    @NotBlank
    @NotNull
    private double cartSubtotal;
    @NotNull
    @Positive(message = "Must be a valid shopping cart id")
    private Long shoppingCartId;
}
