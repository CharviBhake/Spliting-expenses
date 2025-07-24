package com.dailycodebuffer.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ExpenseRequest {
    private Integer amount;
    private String title;
    private String description;
    private List<String> users;

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getUsers() { return users; }
    public void setUsers(List<String> users) { this.users = users; }
}

