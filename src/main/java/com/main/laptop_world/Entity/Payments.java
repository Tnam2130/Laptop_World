package com.main.laptop_world.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tradingCode;
    private String mode;
    private boolean status=false;
    private Date createdAt;
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "payment_user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "payments", cascade = CascadeType.ALL)
    private List<Order> order;

}
