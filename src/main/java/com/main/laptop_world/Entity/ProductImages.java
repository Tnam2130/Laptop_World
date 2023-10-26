package com.main.laptop_world.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product_images")
@AllArgsConstructor
@NoArgsConstructor
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Products products;

    @Column(name = "image_name")
    private String name;
    @Lob
    @Column(name = "url", columnDefinition = "LONGTEXT")
    private String url;


    public ProductImages(String name, String url, Products products){
        super();
        this.name=name;
        this.url=url;
        this.products=products;
    }
}
