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
import com.tripstore.cartitemsmicroservice.products.client.IProductClient;
import com.tripstore.cartitemsmicroservice.products.domain.model.Product;
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
@RequestMapping("/api/tripstore/v1/cart-items/")
public class CartItemsController extends CrudController<CartItem, Long, CartItemResource, CreateCartItemResource, CartItem> {
    private final ICartItemService cartItemsService;
    private final IShoppingCartClient shoppingCartClient;
    private final IProductClient productClient;

    public CartItemsController(ICartItemService cartItemsService, CartItemMapper cartItemsMapper, IShoppingCartClient shoppingCartClient, IProductClient productClient) {
        super(cartItemsService, cartItemsMapper);
        this.cartItemsService = cartItemsService;
        this.shoppingCartClient = shoppingCartClient;
        this.productClient = productClient;
    }
    
    private Optional<ShoppingCart> getShoppingCartFromId(Long shoppingCartId){
        try{
            ShoppingCart shoppingCart = shoppingCartClient.getShoppingCartById(shoppingCartId, false);
            return Optional.of(shoppingCart);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    private Optional<Product> getProductFromId(Long productId) {
        try {
            ResponseEntity<Product> response = productClient.getProductById(productId);
            return response.getStatusCode() == HttpStatus.OK ? Optional.ofNullable(response.getBody()) : Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    private List<CartItemResource> mapCartItems(List<CartItem> cartItems){
        HashMap<Long, Optional<ShoppingCart>> shoppingCarts = new HashMap<>();
        HashMap<Long, Optional<Product>> products = new HashMap<>();

        return cartItems.stream().map(cartItem -> {
                    // get shopping cart with id from map or call to client to try get
                    Optional<ShoppingCart> shoppingCart = shoppingCarts.get(cartItem.getShoppingCartId());
                    CartItemResource resource = mapper.fromModelToResource(cartItem);
                    if(shoppingCart == null){
                        shoppingCart = getShoppingCartFromId(cartItem.getShoppingCartId());
                        shoppingCarts.put(cartItem.getShoppingCartId(), shoppingCart);
                    }
                    shoppingCart.ifPresentOrElse(resource::setShoppingCart, () -> resource.setShoppingCart(null));

                    // get product with id from map or call to client to try get
                    Optional<Product> product = products.get(cartItem.getProductId());
                    if(product == null) {
                        product = getProductFromId(cartItem.getProductId());
                        products.put(cartItem.getProductId(), product);
                    }

                    product.ifPresentOrElse(resource::setProduct, () -> resource.setProduct(null));

                    return resource;
                }).toList();
    }

    private CartItem getCartItem(Long id) {
        Optional<CartItem> cartItem = cartItemsService.getById(id);
        if(cartItem.isEmpty())
            throw new ResourceNotFoundException("Cart Item with id: " + id + " not found");
        return cartItem.get();
    }

    private CartItemResource getCartItemResource(CartItem cartItem){
        // get shopping cart switch id from client and set to resource if it could get it
        CartItemResource resource = mapper.fromModelToResource(cartItem);
        Optional<ShoppingCart> shoppingCart = getShoppingCartFromId(cartItem.getShoppingCartId());
        shoppingCart.ifPresentOrElse(resource::setShoppingCart, () -> resource.setShoppingCart(null));

        // get product switch id from client and set to resource if it could get it
        Optional<Product> product = getProductFromId(cartItem.getProductId());
        product.ifPresentOrElse(resource::setProduct, () -> resource.setProduct(null));
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
        List<CartItem> cartItems = cartItemsService.getAll();
        return ResponseEntity.ok(mapCartItems(cartItems));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartItemResource> createCartItem(@Valid @RequestBody CreateCartItemResource cartItemResource, BindingResult result) {
        if(result.hasErrors()) {
            throw new InvalidCreateResourceException(getErrorsFromResult(result));
        }

        // validate that a shopping cart with the given id exists
        Optional<ShoppingCart> shoppingCart = getShoppingCartFromId(cartItemResource.getShoppingCartId());
        if(shoppingCart.isEmpty())
            throw new InvalidCreateResourceException("The shopping cart id isn't valid");

        // validate that a product with the given id exists
        Optional<Product> product = getProductFromId(cartItemResource.getProductId());
        if(product.isEmpty())
            throw new InvalidCreateResourceException("The product id isn't valid");

        CartItem cartItem = cartItemsService.save(mapper.fromCreateResourceToModel(cartItemResource));
        CartItemResource resource = mapper.fromModelToResource(cartItem);
        resource.setShoppingCart(shoppingCart.get());
        resource.setProduct(product.get());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartItemResource> deleteCartItem(@PathVariable Long id) {
        CartItem item = getCartItem(id);
        cartItemsService.delete(id);
        return ResponseEntity.ok(getCartItemResource(item));
    }

    @GetMapping(value = "shopping-carts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartItemResource>> getByShoppingCartId(@PathVariable Long id) {
        List<CartItem> cartItems = cartItemsService.findByShoppingCartId(id);
        return ResponseEntity.ok(mapCartItems(cartItems));
    }

    @DeleteMapping(value = "shopping-carts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartItemResource>> deleteByShoppingCartId(@PathVariable Long id) {
        List<CartItem> cartItems = cartItemsService.deleteCartItemsByShoppingCartId(id);
        return ResponseEntity.ok(mapCartItems(cartItems));
    }

    @DeleteMapping(value = "products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartItemResource>> deleteByProductId(@PathVariable Long id) {
        List<CartItem> cartItems = cartItemsService.deleteCartItemsByProductId(id);
        return ResponseEntity.ok(mapCartItems(cartItems));
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
