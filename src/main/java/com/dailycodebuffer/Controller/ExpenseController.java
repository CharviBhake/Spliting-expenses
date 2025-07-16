package com.dailycodebuffer.Controller;


import com.dailycodebuffer.Entity.Expense;
import com.dailycodebuffer.Repository.ExpenseRepository;
import com.dailycodebuffer.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private ExpenseRepository expenseRepository;

    @PostMapping
    public ResponseEntity<?> addingExpense(@RequestBody Expense expense) {
        expenseService.SaveNewExpense(expense);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("{Id}")
    public ResponseEntity<Expense> getAllExpenses(@RequestBody Expense expense, @PathVariable String Id) {
        Optional<Expense> expense1 = expenseService.getExpense(Id);
        if (expense1 != null) {
            Expense expense2 = expense1.orElse(null);
            expense2.setAmount(expense.getAmount());
            expense2.setTitle(expense.getTitle());
            expense2.setDescription(expense.getDescription());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}