package com.tripstore.cartitemsmicroservice.service.impl;

import com.tripstore.cartitemsmicroservice.model.CartItems;
import com.tripstore.cartitemsmicroservice.repository.CartItemsRepository;
import com.tripstore.cartitemsmicroservice.service.CartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemsServiceImpl implements CartItemsService {
    private final CartItemsRepository cartItemsRepository;

    @Autowired
    public CartItemsServiceImpl(CartItemsRepository cartItemsRepository) {
        this.cartItemsRepository = cartItemsRepository;
    }

    @Override
    public List<CartItems> getAllCartItems() {
        return cartItemsRepository.findAll();
    }

    @Override
    public CartItems getCartItemsById(int id) {
        return cartItemsRepository.findById(id).orElse(null);
    }

    @Override
    public CartItems saveCartItems(CartItems cartItems) {
        return cartItemsRepository.save(cartItems);
    }

    @Override
    public CartItems updateCartItems(int id, CartItems cartItems) {
        CartItems cartItemsToUpdate = cartItemsRepository.findById(id).orElse(null);
        if (cartItemsToUpdate != null) {
            cartItemsToUpdate.setProductQuantity(cartItems.getProductQuantity());
            cartItemsToUpdate.setCartSubtotal(cartItems.getCartSubtotal());
            cartItemsToUpdate.setProductId(cartItems.getProductId());
            cartItemsToUpdate.setShoppingCartId(cartItems.getShoppingCartId());
            return cartItemsRepository.save(cartItemsToUpdate);
        } else {
            return null;
        }
    }

    @Override
    public void deleteCartItems(int id) {
        cartItemsRepository.deleteById(id);
    }

    @Override
    public List<CartItems> getCartItemsByShoppingCartId(int id) {
        return cartItemsRepository.findByShoppingCartId(id);
    }
}
