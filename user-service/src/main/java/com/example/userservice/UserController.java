package com.example.userservice;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


    @RestController
    @RequestMapping("/users")
    public class UserController {
        private final UserService userService;

        @Autowired
        public UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping("/{id}")
        public User getUserbyId(@PathVariable Long id) {
            return userService.getUser(id);
        }

        @GetMapping("username/{username}")
        public User getUserbyUsername(@PathVariable String username) {
            return userService.getUsername(username);
        }

        @GetMapping("email/{email}")
        public User getUserbyEmail(@PathVariable String email) {
            return userService.getEmail(email);
        }

        @GetMapping("/IsLoggedIn")
        public String isLoggedIn(HttpSession session) {
            if(session.getAttribute("userId") == null){
                return "You are not logged in your account!";
            }

            return session.getAttribute("userId").toString();
        }

        @PostMapping("/register")
        public ResponseEntity<User> registerUser(@RequestBody User user) {
            User newUser = userService.registerUser(user);

            return new ResponseEntity<>(newUser , HttpStatus.OK);
        }

        @PostMapping("/login")
        public ResponseEntity<User> loginUser(@RequestBody User user, HttpSession session) {
            User loginUser = userService.getUsername(user.getUsername());
            if(loginUser == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(!loginUser.getPassword().equals(user.getPassword())){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            session.setAttribute("userId", loginUser.getId());
            session.setAttribute("email",loginUser.getEmail());
            return new ResponseEntity<>(loginUser, HttpStatus.OK);
        }

        @DeleteMapping("/logout")
        public ResponseEntity<User> logoutUser(HttpSession session) {
            if(session.getAttribute("userId") == null){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            session.invalidate();
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }
