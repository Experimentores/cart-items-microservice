package com.tripstore.cartitemsmicroservice.repository;

import com.tripstore.cartitemsmicroservice.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {
    List<CartItems> findByShoppingCartId(int id);
}
