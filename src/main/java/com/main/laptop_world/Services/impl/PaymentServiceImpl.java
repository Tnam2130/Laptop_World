package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Payments;
import com.main.laptop_world.Repository.PaymentRepository;
import com.main.laptop_world.Services.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
PaymentRepository paymentRepository;
public PaymentServiceImpl(PaymentRepository paymentRepository){
    this.paymentRepository=paymentRepository;
}
    @Override
    public Payments savePayment(Payments payments) {
        return paymentRepository.save(payments);
    }
}
