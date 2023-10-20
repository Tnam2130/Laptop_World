package com.main.laptop_world.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping("/cart")
    public String getCart(Model model){
        model.addAttribute("title","Giỏ hàng");
        return "cart/cart";
    }
}
