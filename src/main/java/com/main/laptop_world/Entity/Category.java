package com.main.laptop_world.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "main_categories")
@SecondaryTable(name = "sub_categories", pkJoinColumns = @PrimaryKeyJoinColumn(name = "category_id"))
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String mainName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Products> products;

    @Embedded
    private CategoryEmbeddable categoryEmbeddable;
}

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
class CategoryEmbeddable {
    @Column(name = "category_name", table = "sub_categories")
    private String subName;
}