package com.dailycodebuffer.Controller;

import com.dailycodebuffer.Entity.User;
import com.dailycodebuffer.Service.UserDetailsServiceImpl;
import com.dailycodebuffer.Service.UserService;
import com.dailycodebuffer.Utility.JWTUTIL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JWTUTIL jwtutil;
    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signup(@RequestBody User user){
        User user1=new User();
        user1.setEmail(user.getEmail());
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());

        userService.saveNewUser(user1);
        String token = jwtutil.generateToken(user.getUsername());

        return ResponseEntity.ok("Bearer " + token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            UserDetails userDetails=userDetailsService.loadUserByUsername(user.getUsername());
            String jwt=jwtutil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch(Exception e){
            log.error("Exception occured while createAuthentication",e);
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/health-check")
    public String HealthCheck(){return "OK";}

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
