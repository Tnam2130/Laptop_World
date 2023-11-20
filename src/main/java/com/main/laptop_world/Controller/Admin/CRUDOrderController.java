package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.Order;
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


    public CRUDOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getOrderForm(Model model) {
        List<Order> orderList = orderService.findAll();
        orderList.sort(Comparator.comparing(Order::getUpdatedAt).reversed());
        model.addAttribute("orders", orderList);
        return "admin/QuanLyDonHang";
    }

    @GetMapping(value = "/admin/order/delete/{id}")
    public String deleteColor(@PathVariable Long id, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Delete successfully");
        orderService.deleteOrderById(id);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/update/id={id}")
    public String getUpdateCategory(@PathVariable("id") Long id, Model model, @ModelAttribute Order order) {
        Order orders = orderService.findOrderById(id);
        model.addAttribute("orders", orders);

        return "admin/Update/updateOrder";
    }

    @PostMapping("/admin/order/update")
    public String updateCategory(@RequestParam Long id, @RequestParam String status, RedirectAttributes ra, @ModelAttribute Order order) {

        Order orders = orderService.findOrderById(id);
        String currentStatus = orders.getStatus();

        switch (currentStatus) {
            case "Pending":
                switch (status) {
                    case "Out Of Stock":
                    case "Cancel":
                        ra.addFlashAttribute("message", "Update successfully");
                        orderService.updateOrder(id, status);
                        break;
                    default:
                        return "redirect:/admin/order?error";
                }
                break;

            case "Out Of Stock":
                switch (status) {
                    case "Delivering":
                    case "Cancel":
                        ra.addFlashAttribute("message", "Update successfully");
                        orderService.updateOrder(id, status);
                        break;
                    default:
                        return "redirect:/admin/order?error";
                }
                break;

            case "Delivering":
                switch (status) {
                    case "Delivered":
                    case "Cancel":
                        ra.addFlashAttribute("message", "Update successfully");
                        orderService.updateOrder(id, status);
                        break;
                    default:
                        return "redirect:/admin/order?error";
                }
                break;
        }

        return "redirect:/admin/order";
        }
    }

