package com.main.laptop_world.Controller.User;

import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Entity.ProductImages;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Repository.specification.ProductSpecification;
import com.main.laptop_world.Services.*;
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

    ProductService productService;
    CategoryService categoryService;
    ProductImgService imgService;
    public ProductController(  ProductService productService,
                               CategoryService categoryService,
                               ProductImgService imgService){
        this.productService=productService;
        this.categoryService=categoryService;
        this.imgService=imgService;
    }




    @GetMapping("/collections")
    public String getCollectionForm(Model model){
        List<Category> categories = categoryService.findAllCategory();
        model.addAttribute("title", "Tất cả sản phẩm");
        model.addAttribute("categories", categories);
        model.addAttribute("productList", productService.findAllProduct());
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
        List<Products> relatedProduct=productService.findAllProduct(spec);
        model.addAttribute("relatedProduct", relatedProduct);
//
        Products product= productService.getProductById(productId);
        model.addAttribute("title",product.getName());

        Products products = productService.getProductById(productId);
//        List<ProductImages> productImages = proImgService.findByProductId(productId);
//        model.addAttribute("productImages", productImages);
        model.addAttribute("product", products);
        return "/products/product-detail";
    }
    @RequestMapping("/search")
    public String searchProducts(Model model, @Param("keyword") String keyword) {

        List<Products> productList = productService.findByKeyword(keyword);
        model.addAttribute("productList", productList);
        return "/products/products";
    }
}
