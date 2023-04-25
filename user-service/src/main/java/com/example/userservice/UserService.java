package com.example.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if(userRepository.findByUsername(user.getUsername()) != null){
            throw new IllegalArgumentException("Username exists");
        }
        if(userRepository.findByEmail(user.getEmail()) != null){
            throw new IllegalArgumentException("Email exists");
        }
        return userRepository.save(user);
    }
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}

