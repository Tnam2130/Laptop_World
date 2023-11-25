package com.main.laptop_world.Controller;

import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.CartService;
import com.main.laptop_world.Services.CategoryService;
import com.main.laptop_world.Services.ProductService;
import com.main.laptop_world.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private ProductService productService;
    private CategoryService categoryService;

    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService=categoryService;
    }

    @RequestMapping({"/", "/home", "/index"})
    public String index(Model model) {
        List<Products> productList = productService.findAllProduct();
        List<Category> categories=categoryService.findAllCategory();
        List<Products> filteredProducts = new ArrayList<>();

        for (Category category : categories) {
            // Lọc sản phẩm theo danh mục và trạng thái
            List<Products> categoryProducts = productList.stream()
                    .filter(product -> product.getStatus() && product.getCategory().getId() == category.getId())
                    .collect(Collectors.toList());

            // Trộn danh sách ngẫu nhiên cho từng danh mục
            Collections.shuffle(categoryProducts);

            // Lấy 3 sản phẩm đầu tiên cho từng danh mục
            List<Products> selectedProducts = categoryProducts.stream().limit(3).toList();

            // Thêm vào danh sách chính
            filteredProducts.addAll(selectedProducts);
        }
        model.addAttribute("title", "Laptop World - Thế giới Laptop");
        model.addAttribute("filteredProducts", filteredProducts);
        model.addAttribute("categories", categories);
        return "index";
    }
}
