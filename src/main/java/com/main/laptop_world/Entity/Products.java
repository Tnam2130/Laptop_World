package com.main.laptop_world.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String name;
    @Column(name = "product_price")
    private BigDecimal price;
    @Column(name = "product_old_price")
    private BigDecimal oldPrice;

    @Column(name = "short_description")
    private String shortDesc;
    private Double discount;
    private int quantity;
    private Boolean status;

    // MappedBy trỏ tới tên biến products nằm trong ProductVersion
    // LAZY để tránh việc truy xuất dữ liệu không cần thiết. Lúc nào cần thì mới query
    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<ProductVersion> versions;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<ProductColor> color;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Description> description;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Specifications> specifications;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<ProductImages> images;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Cart> carts;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;


    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;
}
