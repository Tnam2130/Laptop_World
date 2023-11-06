package com.main.laptop_world.Controller.User;

import com.main.laptop_world.Entity.Cart;
import com.main.laptop_world.Entity.Order;
import com.main.laptop_world.Entity.OrderItem;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.CartService;
import com.main.laptop_world.Services.OrderItemService;
import com.main.laptop_world.Services.OrderService;
import com.main.laptop_world.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    CartService cartService;
    UserService userService;
    OrderService orderService;
    OrderItemService orderItemService;
    public OrderController(CartService cartService, UserService userService,
                           OrderService orderService, OrderItemService orderItemService){
        this.cartService=cartService;
        this.userService=userService;
        this.orderService=orderService;
        this.orderItemService=orderItemService;
    }
    @GetMapping("/order/order={orderId}")
    public String getFormOrder(@PathVariable Long orderId, Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        Long userId = user.getId();
        Order order= orderService.findOrderById(orderId);
        List<OrderItem> orderItemList = orderItemService.getOrderByOrderItem(order);
//        System.out.println(orderItemList.toString());
        BigDecimal totalAmount=orderItemService.calculateTotalPriceForUser(orderId,userId);
        model.addAttribute("title", "Thanh toán");
        model.addAttribute("order", order);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("orderItemList", orderItemList);
        return "orders/order";
    }
    @PostMapping("/order/checkout")
    public String checkoutCart(Principal principal){
        User user = userService.findByUsername(principal.getName());
        Long userId = user.getId();
        Long or = cartService.createOrderFromCart(userId);
        return "redirect:/order/order=" + or;
    }

    @GetMapping("/order/history")
    public String getOrderHistory(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        Long userId = user.getId();
        List<Order> orderList= orderService.findByUserId(userId);
        model.addAttribute("orderList", orderList);
        return "orders/OrderHistory";
    }
}
