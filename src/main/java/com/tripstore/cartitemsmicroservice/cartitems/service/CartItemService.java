package com.tripstore.cartitemsmicroservice.cartitems.service;

import com.crudjpa.service.impl.CrudService;
import com.tripstore.cartitemsmicroservice.cartitems.domain.services.ICartItemService;
import com.tripstore.cartitemsmicroservice.cartitems.domain.model.CartItem;
import com.tripstore.cartitemsmicroservice.cartitems.persistence.repository.ICartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartItemService extends CrudService<CartItem, Long> implements ICartItemService {
    private final ICartItemRepository cartItemsRepository;

    @Autowired
    public CartItemService(ICartItemRepository cartItemsRepository) {
        super(cartItemsRepository);
        this.cartItemsRepository = cartItemsRepository;
    }
   @Override
    public List<CartItem> findByShoppingCartId(Long shoppingCartId){
        return cartItemsRepository.findCartItemsByShoppingCartId(shoppingCartId);
   }
    @Override
    public List<CartItem> findByProductQuantity(int productQuantity) {
        return cartItemsRepository.findCartItemsByProductQuantity(productQuantity);
    }
    @Override
    public List<CartItem> findByCartSubtotal(double cartSubtotal){
        return cartItemsRepository.findCartItemsByCartSubtotal(cartSubtotal);
    }
    @Override
    @Transactional
    public  List<CartItem> deleteCartItemsByShoppingCartId(Long shoppingCartId){
        return cartItemsRepository.deleteCartItemsByShoppingCartId(shoppingCartId);
    }
}
