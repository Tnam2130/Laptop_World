package com.main.laptop_world.Controller.User;

import com.main.laptop_world.Entity.Brand;
import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Entity.ProductImages;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Repository.specification.ProductSpecification;
import com.main.laptop_world.Services.*;
import com.main.laptop_world.Filters.FilterCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@SessionAttributes("filterCriteria")
public class ProductController {
    private static final int PAGE_SIZE = 6;

    ProductService productService;
    CategoryService categoryService;
    ProductImgService imgService;
    BrandService brandService;

    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             ProductImgService imgService, BrandService brandService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.imgService = imgService;
        this.brandService=brandService;
    }

    @ModelAttribute("filterCriteria") // Nạp FilterCriteria từ session vào mỗi request
    public FilterCriteria getFilterCriteria() {
        return new FilterCriteria();
    }

    @GetMapping("/collections")
    public String getCollectionForm(Model model, @ModelAttribute("filterCriteria") FilterCriteria filterCriteria, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 6;
        List<Category> categories = categoryService.findAllCategory();
        List<Brand> brandList=brandService.findAllBrand();
        Page<Products> productPage = productService.productFilterAndPaginate(
                filterCriteria.getCategoryId(),
                filterCriteria.getBrandId(),
                filterCriteria.getPriceSort(),
                page,
                PAGE_SIZE
        );
        model.addAttribute("title", "Tất cả sản phẩm");
        model.addAttribute("categories", categories);
        model.addAttribute("brandList", brandList);
        model.addAttribute("productPage", productPage);
        return "products/products";
    }

    @RequestMapping(value = "/collections/filters", method = {RequestMethod.GET, RequestMethod.POST})
    public String handleFilterProduct(
            @ModelAttribute("filterCriteria") FilterCriteria filterCriteria,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "brandId", required = false) Long brandId,
            @RequestParam(name = "priceSort", required = false) String priceSort,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            Model model, SessionStatus sessionStatus) {
        if (categoryId != null || brandId != null || priceSort != null) {
            filterCriteria.setCategoryId(categoryId);
            filterCriteria.setBrandId(brandId);
            filterCriteria.setPriceSort(priceSort);
        } else {
            // Đảm bảo rằng filterCriteria chỉ được xóa nếu không sử dụng bộ lọc và có sử dụng bộ phân trang
            if (filterCriteria.getCategoryId() != null || filterCriteria.getBrandId() !=null ||
                    filterCriteria.getPriceSort() != null) {
                filterCriteria.clear(); // Xóa thông tin bộ lọc
                sessionStatus.setComplete();

            }
        }

        Page<Products> productPage = productService.productFilterAndPaginate(
                filterCriteria.getCategoryId(),
                filterCriteria.getBrandId(),
                filterCriteria.getPriceSort(),
                page,
                PAGE_SIZE
        );

        List<Category> categories = categoryService.findAllCategory();
        List<Brand> brandList=brandService.findAllBrand();
        model.addAttribute("title", "Tất cả sản phẩm");
        model.addAttribute("categoryId", filterCriteria.getCategoryId());
        model.addAttribute("brandId", filterCriteria.getBrandId());
        model.addAttribute("sort", filterCriteria.getPriceSort());
        model.addAttribute("categories", categories);
        model.addAttribute("brandList", brandList);
        model.addAttribute("productPage", productPage);

        return "products/products";
    }

    @GetMapping("/collections/product={productId}&category={categoryId}")
    public String getProductDetail(@PathVariable("productId") Long productId,
                                   @PathVariable("categoryId") Long categoryId,
                                   Model model) {
//        Get categoryId
        Specification<Products> spec = Specification.where(null);
        if (categoryId != null) {
            spec = spec.and(ProductSpecification.hasCategory(categoryId));
        }
        List<Products> relatedProduct = productService.findAllProduct(spec);
        Products product = productService.getProductById(productId);
        List<ProductImages> productImageList = imgService.findByProductId(productId);
        model.addAttribute("relatedProduct", relatedProduct);
        model.addAttribute("title", product.getName());
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
