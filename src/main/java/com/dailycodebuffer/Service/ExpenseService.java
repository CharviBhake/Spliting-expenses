package com.dailycodebuffer.Service;


import com.dailycodebuffer.Entity.Expense;
import com.dailycodebuffer.Repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public void SaveNewExpense(Expense expense){
        expenseRepository.save(expense);
    }
    public Optional<Expense> getExpense(String id){
        return expenseRepository.findById(id);
    }
}
