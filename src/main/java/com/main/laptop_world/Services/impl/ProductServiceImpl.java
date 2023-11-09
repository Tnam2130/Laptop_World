package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Repository.ProductRepository;
import com.main.laptop_world.Repository.specification.ProductSpecification;
import com.main.laptop_world.Services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProductServiceImpl implements ProductService {
    private ProductRepository repository;
    private ProductSpecification productSpecification;

    public ProductServiceImpl(ProductRepository repository, ProductSpecification productSpecification) {
        this.repository = repository;
        this.productSpecification = productSpecification;
    }

    @Override
    public List<Products> findAllProduct() {
        return repository.findAll();
    }

    @Override
    public Page<Products> findAllProductPageable(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Products> findAllProduct(Specification<Products> spec) {
        return repository.findAll(spec);
    }

    @Override
    public Products getProductById(Long id) {
        Optional<Products> optional = repository.findById(id);
        Products product = null;
        if (optional.isPresent()) {
            product = optional.get();
        } else {
            throw new RuntimeException(" Product not found for id :: " + id);
        }
        return product;
    }

    @Override
    public Products saveProduct(Products product) {
        return repository.save(product);
    }

    @Override
    public Products updateProduct(Products products) {
        products.setName(products.getName());
        products.setPrice(products.getPrice());
        products.setOldPrice(products.getOldPrice());
        products.setShortDesc(products.getShortDesc());
        products.setDiscount(products.getDiscount());
        products.setStatus(products.getStatus());
        products.setCategory(products.getCategory());
        products.setBrand(products.getBrand());
        products.setQuantity(products.getQuantity());
        return repository.save(products);
    }

    @Override
    public void deleteProduct(Products products) {
        this.repository.delete(products);
    }

    @Override
    public Page<Products> productFilterAndPaginate(Long id, Long brandId, String keyword, String priceSort, int page, int pageSize) {
        Pageable pageable;
        if (priceSort != null && priceSort.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(page, pageSize, Sort.by("price"));
        } else if (priceSort != null && priceSort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, pageSize, Sort.by("price").descending());
        } else {
            pageable = PageRequest.of(page, pageSize, Sort.unsorted()); //Sort.unsorted() không áp dụng sắp xếp
        }

        Specification<Products> spec = Specification.where(null);

        if (id != null) {
            spec = spec.and((root, query, builder) -> builder.equal(root.get("category").get("id"), id));
        }
        if (brandId != null) {
            spec = spec.and((root, query, builder) -> builder.equal(root.get("brand").get("id"), brandId));
        }
        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, builder) -> builder.like(root.get("name"), "%" + keyword + "%"));
        }
        return repository.findAll(spec, pageable);
    }

    @Override
    public List<Products> findByKeyword(String keyword) {
        if (keyword != null) {
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
