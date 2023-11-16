package com.tripstore.cartitemsmicroservice.shoppingcart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${tripstore.shopping-carts-service.name}",
        path = "${tripstore.shopping-carts-service.path}/")
public interface IShoppingCartClient {
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getShoppingCartById(@PathVariable Long id);
}
