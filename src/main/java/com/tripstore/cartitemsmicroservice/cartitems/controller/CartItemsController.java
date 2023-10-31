package com.tripstore.cartitemsmicroservice.cartitems.controller;

import com.crudjpa.controller.CrudController;
import com.crudjpa.util.TextDocumentation;
import com.tripstore.cartitemsmicroservice.cartitems.domain.model.CartItem;
import com.tripstore.cartitemsmicroservice.cartitems.domain.services.ICartItemService;
import com.tripstore.cartitemsmicroservice.cartitems.exception.InvalidCreateResourceException;
import com.tripstore.cartitemsmicroservice.cartitems.exception.ResourceNotFoundException;
import com.tripstore.cartitemsmicroservice.cartitems.mapping.CartItemMapper;
import com.tripstore.cartitemsmicroservice.cartitems.resources.CartItemResource;
import com.tripstore.cartitemsmicroservice.cartitems.resources.CreateCartItemResource;
import com.tripstore.cartitemsmicroservice.shoppingcart.client.IShoppingCartClient;
import com.tripstore.cartitemsmicroservice.shoppingcart.domain.model.ShoppingCart;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tripstore/v1/cart-item/")
public class CartItemsController extends CrudController<CartItem, Long, CartItemResource, CreateCartItemResource, CartItem> {
    private final ICartItemService cartItemsService;
    private final IShoppingCartClient shoppingCartClient;

    public CartItemsController(ICartItemService cartItemsService, CartItemMapper cartItemsMapper, IShoppingCartClient shoppingCartClient) {
        super(cartItemsService, cartItemsMapper);
        this.cartItemsService = cartItemsService;
        this.shoppingCartClient = shoppingCartClient;
    }
    
    private Optional<ShoppingCart> getShoppingCartFromId(Long shoppingCartId){
        try{
            ShoppingCart shoppingCart = shoppingCartClient.getShoppingCartById(shoppingCartId, "false");
            return Optional.empty();
        }catch (Exception e){
            return Optional.empty();
        }
    }

    private List<CartItemResource> mapCartItems(List<CartItem> cartItems){
        HashMap<Long, Optional<ShoppingCart>> shoppingcarts = new HashMap<>();
        return cartItems.stream().map(cartItem -> {
                    Optional<ShoppingCart> shoppingCart = shoppingcarts.getOrDefault(cartItem.getShoppingCartId(), Optional.empty());
                    CartItemResource resource = mapper.fromModelToResource(cartItem);
                    if(shoppingCart.isEmpty()){
                        shoppingCart = getShoppingCartFromId(cartItem.getShoppingCartId());
                        shoppingcarts.put(cartItem.getShoppingCartId(), shoppingCart);
                    }
                    shoppingCart.ifPresentOrElse(resource::setShoppingCart, () -> resource.setShoppingCart(null));
                    return resource;
                }).toList();
    }

    private CartItem getCartItem(Long id) throws Exception{
        Optional<CartItem> cartItem = cartItemsService.getById(id);
        if(cartItem.isEmpty()) throw new ResourceNotFoundException("Cart Item with id: " + id + " not found");
        return cartItem.get();
    }

    private CartItemResource getCartItemResource(CartItem cartItem){
        CartItemResource resource = mapper.fromModelToResource(cartItem);
        Optional<ShoppingCart> shoppingCart = getShoppingCartFromId(cartItem.getShoppingCartId());
        shoppingCart.ifPresentOrElse(resource::setShoppingCart, () -> resource.setShoppingCart(null));
        return resource;
    }
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart Item" + TextDocumentation.FOUND),
            @ApiResponse(responseCode = "404", description = "Cart Item" + TextDocumentation.NOT_FOUND),
            @ApiResponse(responseCode = "501", description = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<CartItemResource> getCartItemById(@PathVariable Long id) throws Exception {
        CartItem cartItem = getCartItem(id);
        return ResponseEntity.ok(getCartItemResource(cartItem));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart Items" + TextDocumentation.FOUNDS),
            @ApiResponse(responseCode = "404", description = TextDocumentation.NOT_FOUND),
            @ApiResponse(responseCode = "500", description = TextDocumentation.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<List<CartItemResource>> getAllCartItems() {
        try {
            List<CartItem> cartItems = cartItemsService.getAll();
            return ResponseEntity.ok(mapCartItems(cartItems));
        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.name());
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartItemResource> createCartItem(@Valid @RequestBody CreateCartItemResource cartItemResource, BindingResult result) {
        if(result.hasErrors()) {
            throw new InvalidCreateResourceException(getErrorsFromResult(result));
        }
        Optional<ShoppingCart> shoppingCart;
        try {
            shoppingCart = Optional.ofNullable(shoppingCartClient.getShoppingCartById(cartItemResource.getShoppingCartId(), "false"));
            if(shoppingCart.isEmpty())
                throw new InvalidCreateResourceException("The shopping cart id isn't valid");
        } catch (Exception e) {
            throw new InvalidCreateResourceException("The shopping cart id isn't valid");
        }

        try {
            CartItem cartItem = cartItemsService.save(mapper.fromCreateResourceToModel(cartItemResource));
            CartItemResource resource = mapper.fromModelToResource(cartItem);
            resource.setShoppingCart(shoppingCart.get());
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.name());
        }

    }

    @Override
    protected boolean isValidCreateResource(CreateCartItemResource resource){
        return true;
    }
    @Override
    protected boolean isValidUpdateResource(CartItem cartItem){
        return true;
    }
}
