package com.tripstore.cartitemsmicroservice.products.client;

import com.tripstore.cartitemsmicroservice.products.domain.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name="${tripstore.products-service.name}", path = "${tripstore.products-service.path}/")
public interface IProductClient {
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Product> getProductById(@PathVariable Long id);
}
