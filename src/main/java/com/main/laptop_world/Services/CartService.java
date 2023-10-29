package com.main.laptop_world.Services;


import com.main.laptop_world.Entity.Cart;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    void addToCart(Long userId, Long productId, int quantity);
    void removeToCart(Long cartItemId);
    void deletePaidCartItems(Long userId);
    List<Cart> getCartItems(Long userId);
    BigDecimal calculateTotalPrice(List<Cart> cartItem);
    BigDecimal calculateDiscount(List<Cart> cartItems);
    int getCartItemCount(Long userId);
    void createOrderFormCart(Long userId);
}
