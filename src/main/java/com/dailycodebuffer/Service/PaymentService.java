package com.dailycodebuffer.Service;

import com.dailycodebuffer.Entity.Payment;
import com.dailycodebuffer.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public void savePayement(Payment payment){
        paymentRepository.save(payment);
    }
}
