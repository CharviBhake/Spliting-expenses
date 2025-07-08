package com.dailycodebuffer.Service;

import com.dailycodebuffer.Entity.User;
import com.dailycodebuffer.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();

    private static final Logger logger= LoggerFactory.getLogger(UserService.class);

    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }catch(Exception e){
           // logger.error("error occured for {} :",user.getUsername(),e);
            logger.info("Saving new user: {}", user.getUsername());
            logger.error("hahahaha");
            logger.warn("hahahaha");
            logger.trace("hahahaha");
            return false;
        }
    }
    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public List<User> getAll(){ return userRepository.findAll();}
    public Optional<User> findById(String id){ return userRepository.findById(id);}
    public void deleteByUserName(String id){
        userRepository.deleteById(id);
    }
    public User findByUserName(String userName){ return userRepository.findByUsername(userName);}

}
