package com.main.laptop_world.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Voucher {
    @Id
    @GeneratedValue
    @Column(name = "voucher_id")
    private Long id;

    @Column(name = "voucher_name")
    private String name;
    private Date startDate;
    private Date endDate;

    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher")
    private Products products;

}
