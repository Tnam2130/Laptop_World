package com.main.laptop_world.Controller.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collections")
public class ProductController {
    @GetMapping("/product")
    public String getProductForm(Model model){
        model.addAttribute("title", "Trang Sản phẩm");
        return "products/product-detail";
    }
}
