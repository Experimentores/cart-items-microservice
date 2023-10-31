package com.tripstore.cartitemsmicroservice.cartitems.service;

import com.crudjpa.service.impl.CrudService;
import com.tripstore.cartitemsmicroservice.cartitems.domain.services.ICartItemsService;
import com.tripstore.cartitemsmicroservice.cartitems.exception.ResourceNotFoundException;
import com.tripstore.cartitemsmicroservice.cartitems.domain.model.CartItems;
import com.tripstore.cartitemsmicroservice.cartitems.persistence.repository.ICartItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;

@Service
public class CartItemsServiceImpl extends CrudService<CartItems, Long> implements ICartItemsService {
    private final ICartItemsRepository cartItemsRepository;

    @Autowired
    public CartItemsServiceImpl(ICartItemsRepository cartItemsRepository) {
        super(cartItemsRepository);
        this.cartItemsRepository = cartItemsRepository;
    }

    @Override
    public List<CartItems> getAllCartItems() {
        return cartItemsRepository.findAll();
    }

    @Override
    public CartItems getCartItemsById(Long id) {
        return cartItemsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found with id: " + id));
    }

    @Override
    public CartItems saveCartItems(CartItems cartItems) {
        return cartItemsRepository.save(cartItems);
    }

    @Override
    public CartItems updateCartItems(Long id, CartItems cartItems) {
        CartItems cartItemsToUpdate = cartItemsRepository.findById(id).orElse(null);
        if (cartItemsToUpdate != null) {
            cartItemsToUpdate.setProductQuantity(cartItems.getProductQuantity());
            cartItemsToUpdate.setCartSubtotal(cartItems.getCartSubtotal());
            return cartItemsRepository.save(cartItemsToUpdate);
        } else {
            throw new ResourceNotFoundException("Cart Item not found with id: " + id);
        }
    }

    @Override
    public void deleteCartItems(Long id) {
        Optional<CartItems> cartItemsOptional = cartItemsRepository.findById(id);
        if(cartItemsOptional.isPresent()){
            cartItemsRepository.delete(cartItemsOptional.get());
        } else{
            throw new ResourceNotFoundException("Cart Item not found with id: " + id);
        }
    }

}
