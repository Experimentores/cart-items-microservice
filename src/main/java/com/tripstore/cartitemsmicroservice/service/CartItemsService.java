package com.tripstore.cartitemsmicroservice.service;

import com.tripstore.cartitemsmicroservice.model.CartItems;

import java.util.List;

public interface CartItemsService {
    List<CartItems> getAllCartItems();
    CartItems getCartItemsById(int id);
    CartItems saveCartItems(CartItems cartItems);
    CartItems updateCartItems(int id, CartItems cartItems);
    void deleteCartItems(int id);

    List<CartItems> getCartItemsByShoppingCartId(int id);
}