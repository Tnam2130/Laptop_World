package com.main.laptop_world.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product_specifications")
@AllArgsConstructor
@NoArgsConstructor
public class Specifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "information_id")
    private Long id;
    @Column(name = "information_cpu")
    private String cpu_name;
    @Column(name = "information_ram")
    private String ram_name;
    @Column(name = "information_driver")
    private String hard_drive;
    @Column(name = "information_card")
    private String card;
    @Column(name = "information_screen")
    private String screen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;
}
