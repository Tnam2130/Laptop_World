package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Brand;
import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Repository.BrandRepository;
import com.main.laptop_world.Repository.ProductRepository;
import com.main.laptop_world.Services.BrandService;
import com.main.laptop_world.Services.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BrandServiceImpl implements BrandService {
    private BrandRepository brandRepository;
    private ProductService productService;

    public BrandServiceImpl(BrandRepository brandRepository, ProductService productService) {
        this.brandRepository=brandRepository;
        this.productService= productService;
    }

}
