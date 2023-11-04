package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.*;
import com.main.laptop_world.Repository.ProductColorRepository;
import com.main.laptop_world.Repository.ProductRepository;
import com.main.laptop_world.Repository.ProductVersionRepository;
import com.main.laptop_world.Services.*;
import com.main.laptop_world.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CRUDProductController {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/uploads/products";
    ProductService productService;
    CategoryService categoryService;
    BrandService brandService;
    ProductImgService imgService;
    ProductRepository productRepository;
    ProductVersionRepository productVersionRepository;
    ProductColorRepository productColorRepository;
    ProductVersionService productVersionService;
    ProductColorService productColorService;
    StockService stockService;

    public CRUDProductController(
            ProductService productService,
            BrandService brandService,
            CategoryService categoryService,
            ProductImgService imgService,
            ProductRepository productRepository,
            ProductVersionRepository productVersionRepository,
            ProductColorRepository productColorRepository, StockService stockService) {
        this.productRepository = productRepository;
        this.brandService = brandService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.imgService = imgService;
        this.productVersionRepository = productVersionRepository;
        this.productColorRepository = productColorRepository;
        this.stockService = stockService;
    }

    @RequestMapping("/admin/products")
    public String getProductForm(Model model) {
        List<Products> productList = productService.findAllProduct();
        List<Category> categories = categoryService.findAllCategory();
        List<Brand> brands = brandService.findAllBrand();
        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);
        model.addAttribute("productList", productList);
        model.addAttribute("product", new Products());
        boolean selected = true;
        model.addAttribute("selectedValue", selected);
        return "admin/QuanLySanPham";
    }

    @PostMapping("/admin/products/add")
    public String save(@Valid @ModelAttribute("product") Products product, BindingResult result, RedirectAttributes ra,
                       @RequestParam("files") MultipartFile[] files, @RequestParam("stock") int stockQuantity) throws IOException {
        if (result.hasErrors()) {
            return "admin/QuanLySanPham";
        }
        System.out.println(stockQuantity);
        if (files != null && files.length > 0) {
            try {
                List<ProductImages> imagesList = new ArrayList<>();
                for (MultipartFile file : files) {
                    String fileName = file.getOriginalFilename();
                    String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
                    ProductImages images = new ProductImages(fileName, fileContent, product);
                    imagesList.add(images);
                    FileUploadUtil.saveFile(UPLOAD_DIRECTORY, fileName, file);
                }
                Stock existingStock = stockService.findProductById(product.getId());
                if (existingStock == null) {
                    existingStock = new Stock();
                    existingStock.setQuantity(stockQuantity);
                } else {
                    existingStock.setQuantity(existingStock.getQuantity() + stockQuantity);
                }

                stockService.save(existingStock);
                productService.saveProduct(product);
                product.setImages(imagesList);
                product.setStock(existingStock);
                imgService.saveImageFilesList(imagesList);
                ra.addFlashAttribute("message", "Save successfully");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/update/id={id}")
    public String getUpdateProduct(@PathVariable("id") Long id, Model model, RedirectAttributes ra, @ModelAttribute Products products) {
        Products product = productService.getProductById(id);
        List<Brand> brands = brandService.findAllBrand();
        List<Category> categories = categoryService.findAllCategory();
        model.addAttribute("product", product);
        model.addAttribute("brandList", brands);
        model.addAttribute("categories", categories);
        return "admin/Update/updateProduct";
    }

    @PostMapping("/admin/products/update/id={id}")
    public String updateProduct(@PathVariable("id") Long id, Products products, RedirectAttributes ra,
                                @RequestParam("category") Long categoryId,
                                @RequestParam("brand") Long brandId, @RequestParam("stock") int stockQuantity) {
        Products product = productService.getProductById(id);
        Stock existingStock = stockService.findProductById(product.getId());
        Category existingCategory = categoryService.getCategoryById(categoryId);
        Brand existingBrand = brandService.getBrandById(brandId);
        if (existingStock == null) {
            existingStock = new Stock();
            existingStock.setQuantity(stockQuantity);
        } else {
            existingStock.setQuantity(existingStock.getQuantity() + stockQuantity);
        }
        ra.addFlashAttribute("message", "Update successfully");
        product.setStock(existingStock);
        product.setCategory(existingCategory);
        product.setBrand(existingBrand);
        productService.updateProduct(products);
        return "redirect:/admin/products";
    }

    @RequestMapping(value = "/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Delete successfully");
        Products product = productService.getProductById(id);
        productService.deleteProduct(product);
        return "redirect:/admin/products";
    }

    @RequestMapping("/admin/productsVersion")
    public String getProductVersion(Model model) {
        List<ProductVersion> version = productVersionRepository.findAll();
        List<Products> productList = productService.findAllProduct();
        model.addAttribute("version", version);
        model.addAttribute("productList", productList);
        model.addAttribute("versions", new ProductVersion());

        return "admin/ProductVersion";
    }

    @PostMapping("/admin/productsVersion/add")
    public String addVersion(@ModelAttribute("versions") ProductVersion productVersion,
                             String name, Model model, BindingResult result, RedirectAttributes ra) {
        if (productVersion.getName() == null || productVersion.getName().isEmpty()) {
            List<ProductVersion> version = productVersionRepository.findAll();
            model.addAttribute("version", version);
            result.rejectValue("name", "error.version",
                    "Không được để trống Version name!");
            return "admin/ProductVersion";
        }
        if (productVersionRepository.findByName(name).isPresent()) {
            List<ProductVersion> version = productVersionRepository.findAll();
            model.addAttribute("version", version);
            result.rejectValue("name", "error.version",
                    "Version name không được trùng!");
            return "admin/ProductVersion";
        }
        ra.addFlashAttribute("message", "Save successfully");
        productVersionRepository.save(productVersion);
        return "redirect:/admin/productsVersion";
    }

    @GetMapping("/admin/productsVersion/update/id={id}")
    public String getUpdateProductVersion(@PathVariable("id") Long id, Model model,
                                          @ModelAttribute ProductVersion productVersion) {
        ProductVersion version = productVersionRepository.getById(id);
        model.addAttribute("version", version);

        return "admin/Update/updateVersion";
    }

    @PostMapping("/admin/productsVersion/update")
    public String updateProductVersion(ProductVersion products, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Update successfully");
        productVersionRepository.save(products);
        return "redirect:/admin/productsVersion";
    }

    @GetMapping(value = "/admin/productsVersion/delete/{id}")
    public String deleteVersion(@PathVariable Long id, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Delete successfully");
        productVersionRepository.deleteById(id);
        return "redirect:/admin/productsVersion";
    }

    @RequestMapping("/admin/productsColor")
    public String getProductColor(Model model) {
        List<ProductColor> color = productColorRepository.findAll();
        List<ProductVersion> version = productVersionRepository.findAll();
        List<Products> productList = productService.findAllProduct();
        model.addAttribute("color", color);
        model.addAttribute("versions", version);
        model.addAttribute("productList", productList);
        model.addAttribute("colors", new ProductColor());

        return "admin/ProductColor";
    }

    @PostMapping("/admin/productsColor/add")
    public String addColor(@ModelAttribute("colors") ProductColor productColor,
                           String color, Model model, BindingResult result, RedirectAttributes ra) {
        if (productColor.getColor() == null || productColor.getColor().isEmpty()) {
            List<ProductColor> colors = productColorRepository.findAll();
            model.addAttribute("color", colors);
            result.rejectValue("color", "error.color",
                    "Không được để trống Color name!");
            return "admin/ProductColor";
        }
//        if (productColorRepository.findByName(color).isPresent()) {
//            List<ProductColor> colors = productColorRepository.findAll();
//            model.addAttribute("color", colors);
//            result.rejectValue("color", "error.color",
//                    "Color name không được trùng!");
//            return "admin/ProductColor";
//        }
        ra.addFlashAttribute("message", "Save successfully");
        productColorRepository.save(productColor);
        return "redirect:/admin/productsColor";
    }

    @GetMapping("/admin/productsColor/update/id={id}")
    public String getUpdateColor(@PathVariable("id") Long id, Model model, RedirectAttributes ra, @ModelAttribute ProductColor colors) {
        ProductColor productColor = productColorRepository.getById(id);
        model.addAttribute("color", productColor);
        return "admin/Update/updateColor";
    }

    @PostMapping("/admin/productsColor/update")
    public String updateColor(ProductColor productColor, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Update successfully");
        productColorRepository.save(productColor);
        return "redirect:/admin/productsColor";
    }

    @GetMapping(value = "/admin/productsColor/delete/{id}")
    public String deleteColor(@PathVariable Long id, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Delete successfully");
        productColorRepository.deleteById(id);
        return "redirect:/admin/productsColor";
    }

}
