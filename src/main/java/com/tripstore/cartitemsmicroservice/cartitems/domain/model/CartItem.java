package com.tripstore.cartitemsmicroservice.cartitems.domain.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cart_items")
public class CartItem {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity", length = 50)
    private int quantity;

    @Column(name = "subtotal", length = 50)
    private double subtotal;

    @Column(name = "shopping_cart_id", nullable = false)
    private Long shoppingCartId;

    @Column(name = "product_id", nullable = false)
    private Long productId;
}
