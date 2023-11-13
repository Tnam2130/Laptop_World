package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.DTO.OrderDTO;

import com.main.laptop_world.Repository.OrderRepository;
import com.main.laptop_world.Services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class DashBoardController {
    OrderRepository orderRepository;
    OrderService orderService;
    public DashBoardController( OrderRepository orderRepository,
                                OrderService orderService){
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }
    @GetMapping("admin/dashboard")
    public String thongKePage(Model model) {
        List<OrderDTO> revenueDataList = orderService.getRevenueData();
        model.addAttribute("revenueDataList", revenueDataList);
        return "admin/ThongKe";
    }



}
