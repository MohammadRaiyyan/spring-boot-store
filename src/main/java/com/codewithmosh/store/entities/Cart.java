package com.codewithmosh.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "date_created", insertable = false, updatable = false)
  private LocalDate dateCreated;
  @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
  private Set<CartItem> cartItems = new LinkedHashSet<>();

  public BigDecimal calculateTotalPrice() {
    return cartItems.stream().map(CartItem::calculateTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public CartItem addItem(Product product) {
    var cartItem = getItem(product.getId());
    if (cartItem != null) {
      cartItem.setQuantity(cartItem.getQuantity() + 1);
    } else {
      cartItem = new CartItem();
      cartItem.setCart(this);
      cartItem.setProduct(product);
      cartItem.setQuantity(1);
      cartItems.add(cartItem);
    }
    return cartItem;
  }

  public CartItem getItem(Long productId) {
    return cartItems.stream().filter(item ->
            item.getProduct().getId().equals(productId)
    ).findFirst().orElse(null);
  }

  public void removeItem(Long productId){
    var cartItem = getItem(productId);
    if (cartItem != null){
      cartItems.remove(cartItem);
      cartItem.setCart(null);
    }
  }
  public void clearCart(){
    cartItems.clear();
  }
}
