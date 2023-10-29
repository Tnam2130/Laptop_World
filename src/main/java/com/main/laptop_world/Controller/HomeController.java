package com.main.laptop_world.Controller;

import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.CartService;
import com.main.laptop_world.Services.ProductService;
import com.main.laptop_world.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping({"/", "/home", "/index"})
    public String index(Model model) {
        List<Products> productList = productService.findAllProduct();
        model.addAttribute("title", "Laptop World - Thế giới Laptop");
        model.addAttribute("productList", productList);
        return "index";
    }
}
