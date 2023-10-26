package com.main.laptop_world.Controller.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {
    @GetMapping("/collections")
    public String getCollectionForm(Model model){
        model.addAttribute("title", "Tất cả sản phẩm");
        return "products/products";
    }
    @RequestMapping("/product/productId={productId}")
    public String getProductDetail(@PathVariable("productId") Long productId,
                                   @PathVariable("categoryId") Long categoryId,
                                   Model model){
        model.addAttribute("title", "Trang Sản phẩm");
        return "products/product-detail";
    }
}
