package com.main.laptop_world.Controller.User;

import com.main.laptop_world.Entity.Cart;
import com.main.laptop_world.Entity.Order;
import com.main.laptop_world.Entity.OrderItem;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.*;
import com.main.laptop_world.security.oauth2.CustomOAuth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class OrderController {
    CartService cartService;
    UserService userService;
    OrderService orderService;
    OrderItemService orderItemService;
    GeneralService generalService;

    public OrderController(CartService cartService, UserService userService,
                           OrderService orderService, OrderItemService orderItemService, GeneralService generalService) {
        this.cartService = cartService;
        this.userService = userService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.generalService = generalService;
    }

    @GetMapping("/order/order={orderId}")
    public String getFormOrder(@PathVariable Long orderId, Model model, Principal principal) {
        Long userId = generalService.usernameHandler(principal);
        Order order = orderService.findOrderById(orderId);

        List<OrderItem> orderItemList = orderItemService.getOrderByOrderItem(order);
        BigDecimal totalAmount = orderItemService.calculateTotalPriceForUser(orderId, userId);

        model.addAttribute("title", "Thanh toán");
        model.addAttribute("order", order);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("orderItemList", orderItemList);

        return "orders/order";
    }

    @PostMapping("/order/checkout")
    public String checkoutCart(Principal principal) throws ParseException {
        Long userId = generalService.usernameHandler(principal);
        Long orderId = cartService.createOrderFromCart(userId);
        orderService.updateOrderWithPayment(orderId, userId);
        return "redirect:/order/order=" + orderId;
    }

    @GetMapping("/order/history")
    public String getOrderHistory(Model model, Principal principal) {
        Long userId = generalService.usernameHandler(principal);
        List<Order> orderList = orderService.findByUserId(userId);
        orderList.sort(Comparator.comparing(Order::getCreatedAt).reversed());
        model.addAttribute("title","Lịch sử đơn hàng");
        model.addAttribute("orderList", orderList);
        return "orders/OrderHistory";
    }
    @PostMapping("/order/update={orderId}")
    public String updateCancelOrder(@PathVariable Long orderId) {
        Order order = orderService.findOrderById(orderId);
        order.setStatus("Cancel");
        orderService.saveOrder(order);
        return "redirect:/order/history";
    }
}
