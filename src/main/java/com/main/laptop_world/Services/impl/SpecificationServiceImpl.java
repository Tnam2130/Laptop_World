package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Specifications;
import com.main.laptop_world.Repository.SpecificationRepository;
import com.main.laptop_world.Services.SpecificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecificationServiceImpl implements SpecificationService {

    SpecificationRepository repository;
    @Override
    public List<Specifications> findAllSSpec() {
        return repository.findAll();
    }

    @Override
    public Specifications getSpecById(Long id) {
        Optional<Specifications> optional = repository.findById(id);
        Specifications specifications = null;
        if (optional.isPresent()){
            specifications = optional.get();
        }else {
            throw new RuntimeException("Spec not found for id :: " + id);
        }
        return specifications;
    }

    @Override
    public void saveSpec(Specifications specifications) {
        if (specifications != null){
            this.repository.save(specifications);
        }else {
            System.out.println("Spec is null!!!");
        }
    }

    @Override
    public void deleteSpec(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public void updateSpec(Specifications specifications) {
        specifications.setCpu_name(specifications.getCpu_name());
        specifications.setRam_name(specifications.getRam_name());
        specifications.setHard_drive(specifications.getHard_drive());
        specifications.setCard(specifications.getCard());
        specifications.setScreen(specifications.getScreen());
        specifications.setProducts(specifications.getProducts());
        repository.save(specifications);
    }
}
