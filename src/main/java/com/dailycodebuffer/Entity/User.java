package com.dailycodebuffer.Entity;


import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @NonNull
    private String username;
    @Id
    private String id;
    private String email;
    private String password;



}
