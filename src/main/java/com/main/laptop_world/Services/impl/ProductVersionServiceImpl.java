package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.ProductVersion;
import com.main.laptop_world.Repository.ProductVersionRepository;
import com.main.laptop_world.Services.ProductVersionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class ProductVersionServiceImpl implements ProductVersionService {
    ProductVersionRepository repository;
    public ProductVersionServiceImpl(ProductVersionRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductVersion getProductByVersionId(Long id) {
        Optional<ProductVersion> optional=repository.findById(id);
        ProductVersion productVersion = null;
        if(optional.isPresent()){
            productVersion=optional.get();
        }else {
            throw new RuntimeException(" Product not found for id :: " + id);
        }
        return productVersion;
    }

    @Override
    public Optional<ProductVersion> findVersionByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<ProductVersion> getVersionByProductId(Long productId) {
        return repository.findAllByProductsId(productId);
    }

    @Override
    public void updateProduct(ProductVersion productVersion) {

        productVersion.setName(productVersion.getName());
    }

    @Override
    public List<ProductVersion> findAllProduct() {
        return repository.findAll();
    }

    @Override
    public void saveProduct(ProductVersion product) {
        this.repository.save(product);
    }

    @Override
    public void deleteVersionById(Long versionId) {
        this.repository.deleteById(versionId);
    }
}
