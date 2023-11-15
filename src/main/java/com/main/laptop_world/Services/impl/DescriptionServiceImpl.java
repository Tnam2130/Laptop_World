package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Description;

import com.main.laptop_world.Repository.DescriptionRepository;
import com.main.laptop_world.Services.DescriptionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DescriptionServiceImpl implements DescriptionService {
    public DescriptionRepository repository;

    public DescriptionServiceImpl(DescriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Description getById(Long id) {
        Optional<Description> optional=repository.findById(id);
        Description description = null;
        if(optional.isPresent()){
            description=optional.get();
        }else {
            throw new RuntimeException(" Product not found for id :: " + id);
        }
        return description;
    }

    @Override
    public void updateDesc(Description description) {

    }

    @Override
    public List<Description> findAllDesc() {
        return repository.findAll();
    }

    @Override
    public List<Description> getDescriptionProduct(Long productId) {
        return repository.findByProductsId(productId);
    }

    @Override
    public void saveDesc(Description description) {
        this.repository.save(description);
    }

    @Override
    public void deleteDesc(Description description) {
        this.repository.delete(description);
    }
}
