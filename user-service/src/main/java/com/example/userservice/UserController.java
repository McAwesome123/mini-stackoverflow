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

        @PostMapping("/register")
        public ResponseEntity<User> registerUser(@RequestBody User user) {
            User createdUser = userService.registerUser(user);

            return new ResponseEntity<>(createdUser , HttpStatus.OK);
        }

        @PostMapping("/login")
        public ResponseEntity<User> loginUser(@RequestBody User user, HttpSession session) {
            User loginUser = userService.findByUsername(user.getUsername());
            if(loginUser == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(!loginUser.getPassword().equals(user.getPassword())){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            session.setAttribute("userId", loginUser.getId());
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

        @DeleteMapping("/{id}/delete")
        public ResponseEntity<User> deleteUser(@PathVariable Long id, HttpSession session) {
            if(session.getAttribute("userId") == null){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if(!session.getAttribute("userId").equals(id)){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            userService.getUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public User getUserbyId(@PathVariable Long id) {
            return userService.getUser(id);
        }

        @GetMapping("username/{username}")
        public User getUserbyUsername(@PathVariable String username) {
            return userService.findByUsername(username);
        }

        @GetMapping("email/{email}")
        public User getUserbyEmail(@PathVariable String email) {
            return userService.findByEmail(email);
        }

        @GetMapping("/IsLoggedIn")
        public String isLoggedIn(HttpSession session) {
            if(session.getAttribute("userId") == null){
                return "Not logged in";
            }

            return session.getAttribute("userId").toString();
        }

    }
