package com.tripstore.cartitemsmicroservice.shoppingcart.client;

import com.tripstore.cartitemsmicroservice.shoppingcart.domain.model.ShoppingCart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${tripstore.shopping-carts-service.name}",
        path = "${tripstore.shopping-carts-service.path}/")
public interface IShoppingCartClient {
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable Long id);
}
