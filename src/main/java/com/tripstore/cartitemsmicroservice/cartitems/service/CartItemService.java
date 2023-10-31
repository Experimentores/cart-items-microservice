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
    @Transactional
    public  List<CartItem> deleteCartItemsByShoppingCartId(Long shoppingCartId){
        return cartItemsRepository.deleteCartItemsByShoppingCartId(shoppingCartId);
    }

    @Override
    @Transactional
    public List<CartItem> deleteCartItemsByProductId(Long productId) {
        return cartItemsRepository.deleteCartItemByProductId(productId);
    }
}
