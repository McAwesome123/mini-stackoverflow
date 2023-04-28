package com.example.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if(userRepository.fetchByUsername(user.getUsername()) != null){
            throw new IllegalArgumentException("Username exists!");
        }
        if(userRepository.fetchByEmail(user.getEmail()) != null){
            throw new IllegalArgumentException("Email exists!");
        }
        return userRepository.save(user);
    }
    public User getUser(Long id) {
        return userRepository.fetchById(id).orElse(null);
    }

    public User fetchByUsername(String username) {
        return userRepository.fetchByUsername(username);
    }

    public User fetchByEmail(String email) {
        return userRepository.fetchByEmail(email);
    }

}

