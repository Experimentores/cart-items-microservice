package com.tripstore.cartitemsmicroservice.cartitems.persistence.repository;

import com.tripstore.cartitemsmicroservice.cartitems.domain.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartItemsRepository extends JpaRepository<CartItems, Long> {
}
