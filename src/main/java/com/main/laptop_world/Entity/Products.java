package com.main.laptop_world.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Double price;
    @Column(name = "product_old_price")
    private Double oldPrice;

    @Column(name = "short_description")
    private String shortDesc;
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
    private List<Information> information;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<ProductImages> images;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    private Stock stock;

    @OneToOne(mappedBy = "products")
    private Voucher voucher;
}
