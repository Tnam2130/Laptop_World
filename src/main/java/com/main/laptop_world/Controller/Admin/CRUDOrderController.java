package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.Order;
import com.main.laptop_world.Entity.OrderItem;
import com.main.laptop_world.Services.OrderItemService;
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
    OrderItemService orderItemService;

    public CRUDOrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
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
        List<OrderItem> orderItemList = orderItemService.getOrderByOrderItem(order);
        model.addAttribute("orders", orders);
        model.addAttribute("orderItemList", orderItemList);

        return "admin/Update/updateOrder";
    }

    @PostMapping("/admin/order/update")
    public String updateCategory(@RequestParam Long id, @RequestParam String status, RedirectAttributes ra, @ModelAttribute Order order) {

        Order orders = orderService.findOrderById(id);
        String currentStatus = orders.getStatus();
        System.out.println("Trang thai: "+status + "\nTrang thai hien tai: "+ currentStatus);
        switch (currentStatus) {
            case "Pending":
                if (status.equalsIgnoreCase("Out Of Stock")) {
                    ra.addFlashAttribute("message", "Update successfully");
                    orderService.updateOrder(id, status);
                } else {
                    return "redirect:/admin/order?error";
                }
                break;

            case "Out of Stock":
                if (status.equalsIgnoreCase("Delivering")) {
                    ra.addFlashAttribute("message", "Update successfully");
                    orderService.updateOrder(id, status);
                } else {
                    return "redirect:/admin/order?error";
                }
                break;

            case "Delivering":
                if (status.equalsIgnoreCase("Delivered")) {
                    ra.addFlashAttribute("message", "Update successfully");
                    orderService.updateOrder(id, status);
                } else {
                    return "redirect:/admin/order?error";
                }
                break;
            default:
                return "redirect:/admin/order?error";

        }
        return "redirect:/admin/order";
        }
    }

