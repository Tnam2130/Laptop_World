package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.*;
import com.main.laptop_world.Repository.PaymentRepository;
import com.main.laptop_world.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final CartService cartService;
    private final UserService userService;
    private final OrderItemService orderItemService;
    private final OrderService orderService;

    @Autowired
    @Lazy
    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              CartService cartService, UserService userService,
                              OrderItemService orderItemService,
                              OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.cartService = cartService;
        this.userService = userService;
        this.orderItemService = orderItemService;
        this.orderService = orderService;

    }

    @Override
    public void savePayment(Payments payments) {
        paymentRepository.save(payments);
    }

    @Override
    public Long createVNPayOrderFromCart(Long userId, String vnp_Amount, String vnp_TxnRef, String vnp_TransactionStatus) {
        BigDecimal total = new BigDecimal(vnp_Amount).divide(BigDecimal.valueOf(100), RoundingMode.CEILING);
        System.out.println("Total with VNPay: " + total);
        List<Cart> cartList = cartService.getCartToUserId(userId);
        BigDecimal totalAmount = cartService.calculateTotalPrice(cartList);
        BigDecimal discount = totalAmount.subtract(cartService.calculateDiscount(cartList));
        User currentUser = userService.findById(userId);
        Payments payments = new Payments();
        payments.setTradingCode(vnp_TxnRef);
        payments.setUser(currentUser);
        payments.setMode("VNPay");
        payments.setCreatedAt(new Date());
        payments.setStatus(vnp_TransactionStatus.equalsIgnoreCase("00"));
        savePayment(payments);

        Order order = new Order();
        order.setUser(currentUser);
        order.setDiscount(discount);
        order.setTotal(total);
        if (payments.isStatus()) {
            order.setStatus("Pending");
        } else {
            order.setStatus("Cancel");
        }
        order.setUpdatedAt(new Date());
        order.setPayments(payments);

        // Thêm Order vào danh sách order của Payments
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        payments.setOrder(orders);
        savePayment(payments);
        // Lưu đối tượng Order vào cơ sở dữ liệu
        Order savedOrder = orderService.saveOrder(order);

        for (Cart item : cartList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProducts(item.getProducts());
            orderItem.setPrice(item.getProducts().getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setDiscount(discount);
            orderItem.setCreatedAt(new Date());
            orderItem.setUpdatedAt(new Date());
            orderItemService.save(orderItem);
        }
        cartService.deletePaidCartItems(userId);
        return order.getId();
    }

    @Override
    public Payments getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
}
