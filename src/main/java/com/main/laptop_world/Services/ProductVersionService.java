package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.ProductVersion;
import org.springframework.stereotype.Service;
import java.util.List;


public interface ProductVersionService {
    public ProductVersion getProductById(Long id);
    void updateProduct(ProductVersion productVersion);
    List<ProductVersion> findAllProduct();
    void saveProduct(ProductVersion product);
    void deleteProduct(ProductVersion products);
}
