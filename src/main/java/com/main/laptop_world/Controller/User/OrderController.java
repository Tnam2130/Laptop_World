package com.main.laptop_world.Controller.User;

import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.CartService;
import com.main.laptop_world.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class OrderController {
    CartService cartService;
    UserService userService;
    public OrderController(CartService cartService, UserService userService){
        this.cartService=cartService;
        this.userService=userService;
    }
    @GetMapping("/order")
    public String getFormOrder(Model model){
        model.addAttribute("title", "Thanh toán");
        return "orders/order";
    }
    @PostMapping("/order/checkout")
    public String checkoutCart(Principal principal){
        System.out.println(123);
        User user = userService.findByUsername(principal.getName());
        Long userId = user.getId();
        cartService.createOrderFormCart(userId);
        return "redirect:/order";
    }
}
