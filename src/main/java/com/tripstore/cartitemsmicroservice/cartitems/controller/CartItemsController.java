package com.tripstore.cartitemsmicroservice.cartitems.controller;

import com.crudjpa.controller.CrudController;
import com.tripstore.cartitemsmicroservice.cartitems.domain.model.CartItems;
import com.tripstore.cartitemsmicroservice.cartitems.domain.services.ICartItemsService;
import com.tripstore.cartitemsmicroservice.cartitems.mapping.CartItemsMapper;
import com.tripstore.cartitemsmicroservice.cartitems.resources.CartItemsResource;
import com.tripstore.cartitemsmicroservice.cartitems.resources.CreateCartItemsResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tripstore/v1")
public class CartItemsController extends CrudController<CartItems, Long, CartItemsResource, CreateCartItemsResource, CartItems> {
    private final ICartItemsService cartItemsService;
    private final CartItemsMapper cartItemsMapper;

    public CartItemsController(ICartItemsService cartItemsService, CartItemsMapper cartItemsMapper) {
        super(cartItemsService, cartItemsMapper);
        this.cartItemsService = cartItemsService;
        this.cartItemsMapper = cartItemsMapper;
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
    public ResponseEntity<CartItems> getCartItemsById(@PathVariable Long id) {
        return new ResponseEntity<>(cartItemsService.getCartItemsById(id), HttpStatus.OK);
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
    public ResponseEntity<CartItems> updateCartItems(@PathVariable Long id, @RequestBody CartItems cartItems) {
        return new ResponseEntity<>(cartItemsService.updateCartItems(id, cartItems), HttpStatus.OK);
    }

    // Endpoint: /api/tripstore/v1/cart-items/{id}
    // Method: DELETE
    @Transactional
    @DeleteMapping("/cart-items/{id}")
    public ResponseEntity<String> deleteCartItems(@PathVariable Long id) {
        cartItemsService.deleteCartItems(id);
        return new ResponseEntity<>("CartItems deleted successfully", HttpStatus.OK);
    }

}
