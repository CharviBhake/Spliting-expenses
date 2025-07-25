package com.dailycodebuffer.Controller;

import com.dailycodebuffer.Entity.User;
import com.dailycodebuffer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){userService.saveNewUser(user);}

    @GetMapping("/health-check")
    public String HealthCheck(){return "OK";}

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
