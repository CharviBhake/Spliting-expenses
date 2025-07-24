package com.dailycodebuffer.Entity;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Payements_made")
public class Payment {
    @Id
    private String id;
    private String from;
    private String to;
    private int amount;
}
