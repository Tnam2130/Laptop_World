package com.main.laptop_world.Controller.User;

import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Entity.ProductImages;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Repository.specification.ProductSpecification;
import com.main.laptop_world.Services.CateService;
import com.main.laptop_world.Services.ProImgService;
import com.main.laptop_world.Services.ProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProductController {

    ProImgService proImgService;
    @Autowired
    ProService productService;
    @Autowired
    CateService categoryService;




    @GetMapping("/collections")
    public String getCollectionForm(Model model){
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("title", "Tất cả sản phẩm");
        model.addAttribute("categories", categories);
        model.addAttribute("productList", productService.findAll());
        return "products/products";
    }
//    @RequestMapping("/product/productId={productId}")
//    public String getProductDetail(@PathVariable("productId") Long productId,
//                                   @PathVariable("categoryId") Long categoryId,
//                                   Model model){
//        model.addAttribute("title", "Trang Sản phẩm");
//        return "products/product-detail";
//    }
    @GetMapping("/products/productId={productId}&categoryId={categoryId}")
    public String getProductDetail(@PathVariable("productId") Long productId,
                                   @PathVariable("categoryId") Long categoryId,
                                   Model model) {
//        Get categoryId
        Specification<Products> spec = Specification.where(null);
        if (categoryId != null) {
            spec = spec.and(ProductSpecification.hasCategory(categoryId));
        }
        List<Products> relatedProduct=productService.findAll(spec);
        model.addAttribute("relatedProduct", relatedProduct);
//
        String productName=productService.getNameProductByProductId(productId);
        model.addAttribute("title",productName);

        Products products = productService.findById(productId);
        String categoryName = productService.getCategoryNameByProductId(productId);
//        List<ProductImages> productImages = proImgService.findByProductId(productId);
//        model.addAttribute("productImages", productImages);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("product", products);
        return "/products/product-detail";
    }
    @RequestMapping("/search")
    public String searchProducts(Model model, @Param("keyword") String keyword) {

        List<Products> productList = productService.listAll(keyword);
        model.addAttribute("productList", productList);
        return "/products/products";
    }
}
