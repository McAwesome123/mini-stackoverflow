package com.example.userservice;

import org.springframework.data.jpa.repository.JpaRepository;




    public interface UserRepository extends JpaRepository<User, Long> {
        User fetchByUsername(String username);
        User fetchByEmail(String email);
        User fetchById(long id);
    }
