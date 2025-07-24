package com.dailycodebuffer.Repository;

import com.dailycodebuffer.Entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment,String> {
}
