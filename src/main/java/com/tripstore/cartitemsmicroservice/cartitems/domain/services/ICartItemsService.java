package com.tripstore.cartitemsmicroservice.cartitems.domain.services;

import com.crudjpa.service.ICrudService;
import com.tripstore.cartitemsmicroservice.cartitems.domain.model.CartItems;

import java.util.List;

public interface ICartItemsService extends ICrudService<CartItems, Long> {
    List<CartItems> getAllCartItems();
    CartItems getCartItemsById(Long id);
    CartItems saveCartItems(CartItems cartItems);
    CartItems updateCartItems(Long id, CartItems cartItems);
    void deleteCartItems(Long id);
}
