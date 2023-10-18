package com.tripstore.cartitemsmicroservice.controller;

import com.tripstore.cartitemsmicroservice.model.CartItems;
import com.tripstore.cartitemsmicroservice.service.CartItemsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tripstore/v1")
public class CartItemsController {
    private final CartItemsService cartItemsService;

    @Autowired
    public CartItemsController(CartItemsService cartItemsService) {
        this.cartItemsService = cartItemsService;
    }

    // Endpoint: /api/tripstore/v1/cart-items
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/cart-items")
    public ResponseEntity<List<CartItems>> getAllCartItems() {
        return new ResponseEntity<>(cartItemsService.getAllCartItems(), HttpStatus.OK);
    }

    // Endpoint: /api/tripstore/v1/cart-items/{id}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/cart-items/{id}")
    public ResponseEntity<CartItems> getCartItemsById(@PathVariable int id) {
        return new ResponseEntity<>(cartItemsService.getCartItemsById(id), HttpStatus.OK);
    }

    // Endpoint: /api/tripstore/v1/cart-items/shopping-carts/{id}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/cart-items/shopping-carts/{id}")
    public ResponseEntity<List<CartItems>> getCartItemsByShoppingCartId(@PathVariable int id) {
        return new ResponseEntity<>(cartItemsService.getCartItemsByShoppingCartId(id), HttpStatus.OK);
    }

    // Endpoint: /api/tripstore/v1/cart-items
    // Method: POST
    @Transactional
    @PostMapping("/cart-items")
    public ResponseEntity<CartItems> createCartItems(@RequestBody CartItems cartItems) {
        return new ResponseEntity<>(cartItemsService.saveCartItems(cartItems), HttpStatus.CREATED);
    }

    // Endpoint: /api/tripstore/v1/cart-items/{id}
    // Method: PUT
    @Transactional
    @PutMapping("/cart-items/{id}")
    public ResponseEntity<CartItems> updateCartItems(@PathVariable int id, @RequestBody CartItems cartItems) {
        return new ResponseEntity<>(cartItemsService.updateCartItems(id, cartItems), HttpStatus.OK);
    }

    // Endpoint: /api/tripstore/v1/cart-items/{id}
    // Method: DELETE
    @Transactional
    @DeleteMapping("/cart-items/{id}")
    public ResponseEntity<String> deleteCartItems(@PathVariable int id) {
        cartItemsService.deleteCartItems(id);
        return new ResponseEntity<>("CartItems deleted successfully", HttpStatus.OK);
    }

}
