package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Entity.Specifications;
import com.main.laptop_world.Repository.ProductRepository;
import com.main.laptop_world.Repository.specification.ProductSpecification;
import com.main.laptop_world.Services.ProductService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class ProductServiceImpl implements ProductService {
    private ProductRepository repository;
    private ProductSpecification productSpecification;
    public ProductServiceImpl(ProductRepository repository, ProductSpecification productSpecification){
        this.repository=repository;
        this.productSpecification=productSpecification;
    }

    @Override
    public List<Products> findAllProduct() {
        return repository.findAll();
    }

    @Override
    public List<Products> findAllProduct(Specification<Products> spec) {
        return repository.findAll(spec);
    }

    @Override
    public Products getProductById(Long id) {
        Optional<Products> optional=repository.findById(id);
        Products product = null;
        if(optional.isPresent()){
            product=optional.get();
        }else {
            throw new RuntimeException(" Product not found for id :: " + id);
        }
        return product;
    }




    @Override
    public void saveProduct(Products product) {
        this.repository.save(product);
    }

    @Override
    public void deleteProduct(Products products) {
        this.repository.delete(products);
    }

    @Override
    public List<Products> productFilter(Long categoryId, String priceSort) {
        Specification<Products> spec = Specification.where(null);
        if (categoryId != null) {
            spec = spec.and(ProductSpecification.hasCategory(categoryId));
        }

        if (priceSort != null) {
            if (priceSort.equalsIgnoreCase("asc")) {
                spec = spec.and(ProductSpecification.sortByPriceAsc());
            } else if (priceSort.equalsIgnoreCase("desc")) {
                spec = spec.and(ProductSpecification.sortByPriceDesc());
            }
        }
        return repository.findAll(spec);
    }

    @Override
    public List<Products> findByKeyword(String keyword) {
        if(keyword != null){
            return repository.findAll(keyword);
        }
        return repository.findAll();
    }

    @Override
    public List<Products> searchProducts(String searchTerm, String category) {
        if (searchTerm != null && !searchTerm.isEmpty() && category != null && !category.isEmpty()) {
            return repository.findBySearchTermAndCategory(searchTerm, category);
        } else if (searchTerm != null && !searchTerm.isEmpty()) {
            return repository.findBySearchTerm(searchTerm);
        } else {
            return repository.findAll();
        }
    }
}
