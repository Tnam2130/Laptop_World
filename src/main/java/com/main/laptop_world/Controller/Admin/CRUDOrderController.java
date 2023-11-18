package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.Category;
import com.main.laptop_world.Entity.Order;
import com.main.laptop_world.Repository.OrderItemRepository;
import com.main.laptop_world.Repository.OrderRepository;
import com.main.laptop_world.Services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
public class CRUDOrderController {
    OrderService orderService;
    OrderRepository orderRepository;

    public CRUDOrderController(OrderService orderService,
                               OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/admin/order")
    public String getOrderForm(Model model) {
        List<Order> orderList = orderService.findAll();
        orderList.sort(Comparator.comparing(Order::getCreatedAt).reversed());
        model.addAttribute("orders", orderList);
        return "admin/QuanLyDonHang";
    }


    @GetMapping(value = "/admin/order/delete/{id}")
    public String deleteColor(@PathVariable Long id, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Delete successfully");
        orderRepository.deleteById(id);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/update/id={id}")
    public String getUpdateCategory(@PathVariable("id") Long id, Model model, @ModelAttribute Order order) {
        Order orders = orderRepository.getById(id);
        model.addAttribute("orders", orders);
        return "admin/Update/updateOrder";
    }
    @PostMapping("/admin/order/update")
    public String updateCategory(@RequestParam Long id, @RequestParam String status, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Update successfully");
        orderService.updateOrder(id, status);
        return "redirect:/admin/order";
    }
    }
