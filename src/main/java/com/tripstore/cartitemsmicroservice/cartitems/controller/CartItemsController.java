package com.tripstore.cartitemsmicroservice.cartitems.controller;

import com.crudjpa.controller.CrudController;
import com.crudjpa.enums.MapFrom;
import com.crudjpa.util.HttpStatusCheckCode;
import com.crudjpa.util.TextDocumentation;
import com.tripstore.cartitemsmicroservice.cartitems.domain.model.CartItem;
import com.tripstore.cartitemsmicroservice.cartitems.domain.services.ICartItemService;
import com.tripstore.cartitemsmicroservice.cartitems.exception.InvalidCreateResourceException;
import com.tripstore.cartitemsmicroservice.cartitems.mapping.CartItemMapper;
import com.tripstore.cartitemsmicroservice.cartitems.resources.CartItemResource;
import com.tripstore.cartitemsmicroservice.cartitems.resources.CreateCartItemResource;
import com.tripstore.cartitemsmicroservice.products.client.IProductClient;
import com.tripstore.cartitemsmicroservice.products.domain.model.Product;
import com.tripstore.cartitemsmicroservice.shoppingcart.client.IShoppingCartClient;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${tripstore.cart-items-service.path}")
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

    @Override
    protected CartItemResource fromModelToResource(CartItem cartItem, MapFrom from) {
        CartItemResource resource = mapper.fromModelToResource(cartItem);
        resource.setProduct(null);
        if(from != MapFrom.ANY) {
            Optional<Product> product = getProductFromId(cartItem.getProductId());
            product.ifPresent(resource::setProduct);
        }

        return resource;
    }

    private Optional<?> getShoppingCartFromId(Long shoppingCartId){
        try{
            ResponseEntity<?> response = shoppingCartClient.getShoppingCartById(shoppingCartId);
            if(HttpStatusCheckCode.from(response).isOk())
                return Optional.ofNullable(response.getBody());
        } catch (Exception ignored) {}
        return Optional.empty();
    }

    private Optional<Product> getProductFromId(Long productId) {
        try {
            ResponseEntity<Product> response = productClient.getProductById(productId);

            if(HttpStatusCheckCode.from(response).isOk())
                return Optional.ofNullable(response.getBody());

        } catch (Exception ignored) {}
        return Optional.empty();
    }


    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart Item" + TextDocumentation.FOUND),
            @ApiResponse(responseCode = "404", description = "Cart Item" + TextDocumentation.NOT_FOUND),
            @ApiResponse(responseCode = "501", description = TextDocumentation.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<CartItemResource> getCartItemById(@PathVariable Long id) {
        return getById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart Items" + TextDocumentation.FOUNDS),
            @ApiResponse(responseCode = "404", description = TextDocumentation.NOT_FOUND),
            @ApiResponse(responseCode = "500", description = TextDocumentation.INTERNAL_SERVER_ERROR),
    })
    public ResponseEntity<List<CartItemResource>> getAllCartItems() {
        return getAll();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartItemResource> createCartItem(@Valid @RequestBody CreateCartItemResource cartItemResource, BindingResult result) {
        if(result.hasErrors())
            throw new InvalidCreateResourceException(getErrorsFromResult(result));

        return insert(cartItemResource);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartItemResource> deleteCartItem(@PathVariable Long id) {
        return delete(id);
    }


    private List<CartItemResource> mapCartItemsResourceOfShoppingCarts(List<CartItem> shoppingCarts) {
        return shoppingCarts.stream()
                .map(cartItem -> {
                   CartItemResource resource = mapper.fromModelToResource(cartItem);
                   resource.setProduct(null);

                   Optional<Product> product = getProductFromId(cartItem.getProductId());
                   product.ifPresent(resource::setProduct);

                   return resource;
                }).toList();
    }

    ResponseEntity<List<CartItemResource>> cartItemsResponseOfShoppingCarts(List<CartItem> cartItems) {
        if(cartItems.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(mapCartItemsResourceOfShoppingCarts(cartItems));
    }

    @GetMapping(value = "shopping-carts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartItemResource>> getByShoppingCartId(@PathVariable Long id) {
        List<CartItem> cartItems = cartItemsService.findByShoppingCartId(id);
        return cartItemsResponseOfShoppingCarts(cartItems);
    }

    @DeleteMapping(value = "shopping-carts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartItemResource>> deleteByShoppingCartId(@PathVariable Long id) {
        List<CartItem> cartItems = cartItemsService.deleteCartItemsByShoppingCartId(id);
        return cartItemsResponseOfShoppingCarts(cartItems);
    }

    @DeleteMapping(value = "products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartItemResource>> deleteByProductId(@PathVariable Long id) {
        List<CartItem> cartItems = cartItemsService.deleteCartItemsByProductId(id);
        return cartItemsResponseOfShoppingCarts(cartItems);
    }

    @RequestMapping(value = "healthcheck", method = RequestMethod.HEAD)
    ResponseEntity<Void> isOk() {
        return ResponseEntity.ok().build();
    }

    @Override
    protected boolean isValidCreateResource(CreateCartItemResource resource){
        // validate that a shopping cart with the given id exists
        Optional<?> shoppingCart = getShoppingCartFromId(resource.getShoppingCartId());
        if(shoppingCart.isEmpty())
            throw new InvalidCreateResourceException("The shopping cart id isn't valid");

        // validate that a product with the given id exists
        Optional<Product> product = getProductFromId(resource.getProductId());
        if(product.isEmpty())
            throw new InvalidCreateResourceException("The product id isn't valid");
        return true;
    }
    @Override
    protected boolean isValidUpdateResource(CartItem cartItem){
        return true;
    }
}
