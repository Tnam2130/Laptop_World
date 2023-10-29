package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.CartService;
import com.main.laptop_world.Services.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class HeaderController {
    private CartService cartService;
    private UserService userService;
    public HeaderController(CartService cartService, UserService userService){
        this.cartService=cartService;
        this.userService=userService;
    }
    @ModelAttribute("cartItemCount")
    public int getCartItemCount(Principal principal){
        if(principal != null){
            User user = userService.findByUsername(principal.getName());
            return cartService.getCartItemCount(user.getId());
        }
        return 0;
    }
}
