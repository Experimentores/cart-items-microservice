package com.tripstore.cartitemsmicroservice.model;

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
public class CartItems {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity", length = 50)
    private int productQuantity;

    @Column(name = "subtotal", length = 50)
    private double cartSubtotal;

    /*@Column(name = "product_id", nullable = false)
    private int productId;*/
}
