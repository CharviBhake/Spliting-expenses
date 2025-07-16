package com.dailycodebuffer.Repository;

import com.dailycodebuffer.Entity.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRepository extends MongoRepository<Expense,String> {
}
