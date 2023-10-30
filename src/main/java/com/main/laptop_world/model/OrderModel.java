package com.main.laptop_world.model;


import com.main.laptop_world.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {
    private User user;
    private BigDecimal discount;
    private BigDecimal totalAmount;

}
