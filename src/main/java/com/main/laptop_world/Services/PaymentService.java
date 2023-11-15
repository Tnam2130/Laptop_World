package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.Payments;

public interface PaymentService {
    void savePayment(Payments payments);
    Long createVNPayOrderFromCart(Long userId, String vnp_Amount, String vnp_TxnRef, String vnp_TransactionStatus);
    Payments getPaymentByOrderId(Long orderId);

}
