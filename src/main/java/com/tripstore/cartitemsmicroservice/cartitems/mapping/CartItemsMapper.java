package com.tripstore.cartitemsmicroservice.cartitems.mapping;

import com.crudjpa.mapping.IEntityMapper;
import com.tripstore.cartitemsmicroservice.cartitems.domain.model.CartItems;
import com.tripstore.cartitemsmicroservice.cartitems.resources.CartItemsResource;
import com.tripstore.cartitemsmicroservice.cartitems.resources.CreateCartItemsResource;
import com.tripstore.cartitemsmicroservice.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class CartItemsMapper implements IEntityMapper<CartItems, CartItemsResource, CreateCartItemsResource, CartItems> {
    @Autowired
    EnhancedModelMapper mapper;
    @Override
    public CartItems fromCreateResourceToModel(CreateCartItemsResource scoreResource){
        return mapper.map(scoreResource, CartItems.class);
    }
    @Override
    public CartItemsResource fromModelToResource(CartItems score){
        return mapper.map(score, CartItemsResource.class);
    }
    @Override
    public CartItems fromUpdateResourceToModel(CartItems cartItems){
        return cartItems;
    }
}
