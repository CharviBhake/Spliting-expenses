package com.dailycodebuffer.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="Group")
public class Expense {
    @Id
    private String id;
    private String createdBy;
    private String title;
    private int amount;
    private String description;
    private List<String> users;
}
