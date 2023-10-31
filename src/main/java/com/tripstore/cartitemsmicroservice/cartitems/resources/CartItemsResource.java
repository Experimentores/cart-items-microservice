package com.tripstore.cartitemsmicroservice.cartitems.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data()
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsResource extends CreateCartItemsResource{
    private Long id;
}
