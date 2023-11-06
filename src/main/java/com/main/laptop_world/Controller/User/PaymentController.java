package com.main.laptop_world.Controller.User;

import com.main.laptop_world.Entity.DTO.CreatePaymentRequest;
import com.main.laptop_world.Entity.Payments;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.CartService;
import com.main.laptop_world.Services.OrderService;
import com.main.laptop_world.Services.PaymentService;
import com.main.laptop_world.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class PaymentController {
    CartService cartService;
    UserService userService;
    OrderService orderService;
    PaymentService paymentService;

    public PaymentController(CartService cartService, UserService userService, OrderService orderService, PaymentService paymentService) {
        this.cartService = cartService;
        this.userService = userService;
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

//    @PostMapping("/payment/api/create")
//    public ResponseEntity<String> createPayment(@RequestBody CreatePaymentRequest request, Principal principal) {
//        User user = userService.findByUsername(principal.getName());
//        Long userId = user.getId();
//        // Xử lý yêu cầu tạo giao dịch thanh toán ở đây
//        // Tạo một đối tượng Payments từ thông tin request và lưu nó vào cơ sở dữ liệu
//        Payments payment = new Payments();
//        payment.setMode(request.getMode());
//        payment.setStatus(request.getStatus());
//        payment.setUser(request.getUser());
//        payment.setOrder(request.getOrder());
//        paymentService.savePayment(payment);
//
//        // Tạo đơn hàng mới hoặc thực hiện các xử lý khác
//        Long newOrderId = cartService.createOrderFromCart(userId);
//        return ResponseEntity.ok("Payment created, and a new order with ID " + newOrderId + " has been created.");
//    }
}
