package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.Products;
import com.main.laptop_world.Repository.ProductRepository;
import com.main.laptop_world.Repository.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProService {
    @Autowired
    ProductRepository repository;
    @Autowired
    ProductSpecification productSpecification;
    public List<Products> findAll() {
        return repository.findAll();
    }

    public List<Products> listAll(String keyword) {
        if(keyword != null){
            return repository.findAll(keyword);
        }
        return repository.findAll();
    }

    public List<Products> findAll(Specification<Products> spec){
        return repository.findAll(spec);
    }
    public void create(Products products) {
        repository.save(products);
    }

    public void update(Products products) {
        repository.save(products);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    public Products findById(Long id){
        return repository.findById(id).orElse(null);
    }
    /*    C1
        public Page<Products> filterProducts(ProductRequest request){
          Specification<Products> spec = productSpecification.getSpecification(request);
            Specification<Products> spec = Specification.where(null);
          PageRequest pageRequest;
          if(request.getOrder()!=null){
              Sort.Direction direction = request.getOrder().equalsIgnoreCase("asc")
                      ? Sort.Direction.ASC : Sort.Direction.DESC;
              Sort sort = Sort.by(direction, request.getOrderBy());
              pageRequest = PageRequest.of(request.getPage(), request.getSize(),sort);
          }else{
              pageRequest = PageRequest.of(request.getPage(), request.getSize());
          }
          return repository.findAll(spec, pageRequest);
        }*/
//    C2
    public List<Products> filterProducts( Long categoryId, String priceSort){
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
    public String getCategoryNameByProductId(Long id){
        return repository.findCategoryNameByProductId(id);
    }

    public String getNameProductByProductId(Long id){
        return repository.findNameProductById(id);
    }

    public Double getProductPrice(Long productId) {
        Products product = repository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        return product.getPrice();
    }
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
