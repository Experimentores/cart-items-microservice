package com.tripstore.cartitemsmicroservice.cartitems.mapping;

import com.crudjpa.mapping.IEntityMapper;
import com.tripstore.cartitemsmicroservice.cartitems.domain.model.CartItem;
import com.tripstore.cartitemsmicroservice.cartitems.resources.CartItemResource;
import com.tripstore.cartitemsmicroservice.cartitems.resources.CreateCartItemResource;
import com.tripstore.cartitemsmicroservice.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class CartItemMapper implements IEntityMapper<CartItem, CartItemResource, CreateCartItemResource, CartItem> {
    @Autowired
    EnhancedModelMapper mapper;
    @Override
    public CartItem fromCreateResourceToModel(CreateCartItemResource scoreResource){
        return mapper.map(scoreResource, CartItem.class);
    }
    @Override
    public CartItemResource fromModelToResource(CartItem score){
        return mapper.map(score, CartItemResource.class);
    }
    @Override
    public CartItem fromUpdateResourceToModel(CartItem cartItems){
        return cartItems;
    }
}
