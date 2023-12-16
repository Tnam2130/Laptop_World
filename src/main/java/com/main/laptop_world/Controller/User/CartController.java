package com.main.laptop_world.Controller.User;

import com.main.laptop_world.Entity.*;
import com.main.laptop_world.Services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class CartController {
    private CartService cartService;
    private UserService userService;
private GeneralService generalService;
    public CartController(CartService cartService, UserService userService, GeneralService generalService) {
        this.cartService = cartService;
        this.userService = userService;
        this.generalService=generalService;
    }

    @GetMapping("/cart")
    public String showFormCart(Model model, Principal principal) {
        Long userId= generalService.usernameHandler(principal);
        User user= userService.findById(userId);
        List<Cart> cartItems = cartService.getCartItems(userId);
        if(user.getUserDetailEmbeddable() == null){
            return "redirect:/user/profile";
        }else{
            if (!cartItems.isEmpty()) {
                int quantities = cartItems.stream().mapToInt(Cart::getQuantity).sum();
                BigDecimal totalPrice = cartService.calculateTotalPrice(cartItems);
                BigDecimal total = cartService.calculateDiscount(cartItems);
                BigDecimal discount = totalPrice.subtract(total);
                double percentage = 0;
                if (quantities >= 2) {
                    percentage = 5;
                }
                if (quantities >= 5) {
                    percentage = 10;
                }
                if (quantities >= 10) {
                    percentage = 20;
                }
                model.addAttribute("totalPrice", totalPrice);
                model.addAttribute("user", user);
                model.addAttribute("total", total);
                model.addAttribute("discount", discount);
                model.addAttribute("cartItems", cartItems);
                model.addAttribute("title", "Giỏ hàng");
                model.addAttribute("quantities", quantities);
                model.addAttribute("percentage", percentage);
            } else {
                model.addAttribute("title", "Giỏ hàng");
                return "cart/cart";
            }
        }
        return "cart/cart";
    }

    @PostMapping("/cart/add/{productId}")
    public String addToCart(@PathVariable Long productId, Principal principal, @ModelAttribute("quantity") int quantity) {
        Long userId= generalService.usernameHandler(principal);
        User user=userService.findById(userId);
        List<Cart> cartList=user.getCarts();
        if(cartList.size() >= 3){
            System.out.println("Cart is over: " + userId);
            return "redirect:/cart?error";
        }
        cartService.addToCart(userId, productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/cart/remove/{cartId}")
    public String removeCartItem(@PathVariable Long cartId) {
        cartService.removeToCart(cartId);
        return "redirect:/cart";
    }
}
