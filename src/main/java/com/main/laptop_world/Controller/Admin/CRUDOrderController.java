package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.Order;
import com.main.laptop_world.Services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CRUDOrderController {
    OrderService orderService;

    public CRUDOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getOrderForm(Model model) {
        List<Order> orderList = orderService.findAll();
        model.addAttribute("orderList", orderList);
        return "admin/QuanLyDonHang";
    }
}
