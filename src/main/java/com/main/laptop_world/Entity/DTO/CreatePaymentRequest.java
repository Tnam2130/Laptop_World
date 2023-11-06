package com.main.laptop_world.Entity.DTO;

import com.main.laptop_world.Entity.Order;
import com.main.laptop_world.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
    private String mode;
    private String status;
    private User user;
    private Order order;
}
