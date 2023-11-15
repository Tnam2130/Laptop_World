package com.main.laptop_world.Services;


import com.main.laptop_world.Entity.Cart;
import com.main.laptop_world.Entity.Order;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface CartService {
    void addToCart(Long userId, Long productId, int quantity);
    void removeToCart(Long cartItemId);
    List<Cart> getCartToUserId(Long userId);
    void deletePaidCartItems(Long userId);
    List<Cart> getCartItems(Long userId);
    BigDecimal calculateTotalPrice(List<Cart> cartItem);
    BigDecimal calculateDiscount(List<Cart> cartItems);
    int getCartItemCount(Long userId);
    Long createOrderFromCart(Long userId) throws ParseException;
    BigDecimal getTotalPriceByUserId(Long userId);
}
