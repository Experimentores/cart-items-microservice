package com.tripstore.cartitemsmicroservice.cartitems.domain.services;

import com.crudjpa.service.ICrudService;
import com.tripstore.cartitemsmicroservice.cartitems.domain.model.CartItem;

import java.util.List;

public interface ICartItemService extends ICrudService<CartItem, Long> {
    List<CartItem> findByShoppingCartId(Long shoppingCartId);
    List<CartItem> deleteCartItemsByShoppingCartId(Long shoppingCartId);
    List<CartItem> deleteCartItemsByProductId(Long productId);
}
