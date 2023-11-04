package com.main.laptop_world.Services;
import com.main.laptop_world.Entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public interface ProductService {
    List<Products> findAllProduct();
    Page<Products> findAllProductPageable(Pageable pageable);
    List<Products> findAllProduct(Specification<Products> spec);
    public Products getProductById(Long id);
    Products saveProduct(Products product);
    Products updateProduct(Products products);
    void deleteProduct(Products products);
    Page<Products> productFilterAndPaginate (Long categoryId, Long brandId, String priceSort, int page, int pageSize);
    List<Products> findByKeyword(String keyword);
    List<Products> searchProducts(String searchTerm, String category);
}

