package com.dailycodebuffer.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {
    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendEmail("charvibhake2@gmail.com",
                "Tesring java mail sender",
                "hi , aaap kaise hai");
    }
}
