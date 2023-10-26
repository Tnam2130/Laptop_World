package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.ProductImages;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Services.ProductImgService;
import com.main.laptop_world.Services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
public class CRUDProductImageController {
    ProductImgService imgService;
    ProductService productService;
    public CRUDProductImageController(ProductImgService imgService,
                                      ProductService productService){
        this.imgService=imgService;
        this.productService = productService;
    }

    @RequestMapping("/admin/images")
    public String getProductForm(Model model) {
        List<ProductImages> productImages = imgService.getAllImages();
        List<Products> productList = productService.findAllProduct();
        model.addAttribute("productImages", productImages);
        model.addAttribute("productImage",new ProductImages());
        model.addAttribute("productList", productList);

        return "admin/QuanLyHinhAnh";
    }
//    @PostMapping("/admin/images/add")
//    public String save(@ModelAttribute("productImage") ProductImages productImages, @RequestParam MultipartFile file) throws IOException {
//        productImages.setName(file.getOriginalFilename());
//        productImages.setUrl(file.getBytes());
//        productImagesRepository.save(productImages);
//        return "redirect:/admin/images";
//    }
@RequestMapping(value = "/admin/images/delete/{id}")
public String deleteImages(@PathVariable Long id) {
    ProductImages productImages=imgService.getImageById(id);
    imgService.deleteImage(productImages);
    return "redirect:/admin/products";
}
}
