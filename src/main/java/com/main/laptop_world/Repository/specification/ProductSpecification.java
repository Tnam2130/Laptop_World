package com.main.laptop_world.Repository.specification;

import com.main.laptop_world.Entity.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public final class ProductSpecification {
    /* C1
    public Specification<Products> isBelongToCategory(Long id) {
        return (root, query, criteriaBuilder) -> {
            Join<Category, Products> join = root.join(Products_.CATEGORY);
            return criteriaBuilder.equal(join.get(Category_.ID), id);
        };
    }

    public Specification<Products> getSpecification(ProductRequest request) {
        Specification<Products> spec = Specification.where(null);
        if (request.getCategory() != null) {
            spec = spec.and(isBelongToCategory(request.getCategory()));
        }
        return spec;
    }
*/
    public static Specification<Products> hasCategory(Long id) {
        return (root, query, cb) -> {
            if (id == null) {
                return null;
            }
            Join<Products, Category> categoryJoin = root.join(Products_.CATEGORY);
            return cb.equal(categoryJoin.get(Category_.ID), id);
        };
    }

    public static Specification<Products> sortByPriceAsc() {
        return (root, query, cb) -> {
            query.orderBy(cb.asc(root.get(Products_.price)));
            return cb.and();
        };
    }

    public static Specification<Products> sortByPriceDesc() {
        return (root, query, cb) -> {
            query.orderBy(cb.desc(root.get(Products_.price)));
            return cb.and();
        };
    }

    public static Specification<Products> findByCategoryId(Long categoryId){
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }
          Join<Products, Category> join = root.join(Products_.CATEGORY, JoinType.INNER);
            return criteriaBuilder.equal(join.get(Category_.ID),categoryId);
        };
    }
    public static Specification<Products> filterByProductId(Long productId){
        return (root, query, criteriaBuilder) -> {
            if (productId==null){
                return  null;
            }
          Join<ProductImages, Products> join = root.join(Products_.IMAGES, JoinType.INNER);
            return criteriaBuilder.equal(join.get(Products_.ID),productId);
        };
    }
}
