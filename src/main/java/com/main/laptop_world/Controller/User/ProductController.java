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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @RequestMapping(value = "/collections/filers",method = {RequestMethod.GET, RequestMethod.POST})
        public String handleFilterProduct(@RequestParam(name = "categoryId", required = false) Long categoryId,
                                          @RequestParam(name = "priceSort", required = false) String priceSort,
                                          Model model, RedirectAttributes redirectAttributes){
        System.out.println(priceSort);
        if (categoryId != null && priceSort != null) {
            redirectAttributes.addAttribute("categoryId", categoryId);
            redirectAttributes.addAttribute("priceSort", priceSort);
            return "redirect:/collection/filters";
        }
        List<Products> productsList = productService.productFilter(categoryId, priceSort);
        List<Category> categories = categoryService.findAllCategory();
        model.addAttribute("title","Tất cả sản phẩm");
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", priceSort);
        model.addAttribute("categories", categories);
        model.addAttribute("productList", productsList);
        return "products/products";
    }

    @GetMapping("/collections/product={productId}&category={categoryId}")
    public String getProductDetail(@PathVariable("productId") Long productId,
                                   @PathVariable("categoryId") Long categoryId,
                                   Model model)  {
//        Get categoryId
        Specification<Products> spec = Specification.where(null);
        if (categoryId != null) {
            spec = spec.and(ProductSpecification.hasCategory(categoryId));
        }
        List<Products> relatedProduct=productService.findAllProduct(spec);
        Products product= productService.getProductById(productId);
        List<ProductImages> productImageList=imgService.findByProductId(productId);
        model.addAttribute("relatedProduct", relatedProduct);
        model.addAttribute("title",product.getName());
        model.addAttribute("productImages", productImageList);
        model.addAttribute("products", product);
        return "/products/product-detail";
    }
    @RequestMapping("/search")
    public String searchProducts(Model model, @Param("keyword") String keyword) {

        List<Products> productList = productService.findByKeyword(keyword);
        model.addAttribute("productList", productList);
        return "/products/products";
    }
}
