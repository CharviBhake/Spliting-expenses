package com.dailycodebuffer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(Main.class,args);
        ConfigurableEnvironment environment=context.getEnvironment();
        String[] profiles = environment.getActiveProfiles();
        if (profiles.length > 0) {
            System.out.println("Active Profile: " + profiles[0]);
        } else {
            System.out.println("No active profile set.");
        }

    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}