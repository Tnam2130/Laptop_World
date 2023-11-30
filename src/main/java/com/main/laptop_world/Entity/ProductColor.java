package com.main.laptop_world.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product_color")
@AllArgsConstructor
@NoArgsConstructor
public class ProductColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Long id;
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore

    private Products products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version")
    @JsonIgnore
    private ProductVersion version;
}
