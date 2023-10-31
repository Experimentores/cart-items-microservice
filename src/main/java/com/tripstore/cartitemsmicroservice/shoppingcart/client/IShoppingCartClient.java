package com.tripstore.cartitemsmicroservice.shoppingcart.client;

import com.tripstore.cartitemsmicroservice.shoppingcart.domain.model.ShoppingCart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="shopping-cart-service")
public interface IShoppingCartClient {
    @GetMapping("{id}")
    ShoppingCart getShoppingCartById(@PathVariable Long id, @RequestParam String getCartItems);
}
