package com.tripstore.cartitemsmicroservice.cartitems.persistence.repository;

import com.tripstore.cartitemsmicroservice.cartitems.domain.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findCartItemsByShoppingCartId(Long shoppingCartId);
    List<CartItem> deleteCartItemsByShoppingCartId(Long shoppingCartId);
    List<CartItem> findCartItemsByProductQuantity(int productQuantity);
    List<CartItem> findCartItemsByCartSubtotal(double cartSubtotal);
}
