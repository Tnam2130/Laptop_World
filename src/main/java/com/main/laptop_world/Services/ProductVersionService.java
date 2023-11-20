package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.ProductVersion;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface ProductVersionService {
    ProductVersion getProductByVersionId(Long id);
    Optional<ProductVersion> findVersionByName(String name);
    void updateProduct(ProductVersion productVersion);
    List<ProductVersion> findAllProduct();
    void saveProduct(ProductVersion product);
    void deleteVersionById(Long versionId);
}
