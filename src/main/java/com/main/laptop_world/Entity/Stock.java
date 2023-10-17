package com.main.laptop_world.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "stock_id")
    private Long id;

    private int quantity;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stock")
    private List<Products> products;
}
