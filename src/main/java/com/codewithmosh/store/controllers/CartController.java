package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.AddItemToCartRequest;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.dtos.UpdateCartItemRequest;
import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.exceptions.ProductNotFoundException;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.services.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@Tag(name="Carts")
public class CartController {

  private final CartRepository cartRepository;
  private final CartService cartService;

  @PostMapping
  public ResponseEntity<CartDto> createCart() {
    CartDto cartDto = cartService.createCart();
    return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
  }

  @PostMapping("/{cartId}/items")
  public ResponseEntity<CartItemDto> addToCart(@PathVariable UUID cartId, @RequestBody AddItemToCartRequest request) {
    CartItemDto cartItemDto = cartService.addToCart(cartId, request.productId());
    return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
  }

  @GetMapping("/{cartId}")
  public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId) {
    var cartDto = cartService.getCart(cartId);
    return ResponseEntity.ok().body(cartDto);
  }

  @PutMapping("/{cartId}/items/{productId}")
  public ResponseEntity<CartItemDto> updateItem(@PathVariable UUID cartId, @PathVariable Long productId, @Valid @RequestBody UpdateCartItemRequest request) {
    var cartItemDto = cartService.updateItem(cartId, productId, request.quantity());
    return ResponseEntity.ok().body(cartItemDto);
  }

  @DeleteMapping("/{cartId}/items/{productId}")
  public ResponseEntity<Void> removeItem(@PathVariable UUID cartId, @PathVariable Long productId) {
    cartService.removeItem(cartId, productId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{cartId}/items")
  public ResponseEntity<Void> clearCart(@PathVariable UUID cartId) {
    cartService.clearCart(cartId);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(CartNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleCartNotFound() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cart not found."));
  }

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleProductNotFound() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Product not found in the cart."));
  }

}
