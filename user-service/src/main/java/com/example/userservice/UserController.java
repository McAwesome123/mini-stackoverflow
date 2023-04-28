package com.example.userservice;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


    @RestController
    @RequestMapping("/users")
    public class UserController {
        @Autowired
        private final UserService userService;

        @Autowired
        public UserController(UserService userService) {
            this.userService = userService;
        }

        @PostMapping(value="/register", produces=MediaType.APPLICATION_XML_VALUE)
        public ResponseEntity<User> registerUser(@RequestBody User user) {
            try {
                User createdUser = userService.registerUser(user);

                return new ResponseEntity<>(createdUser , HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }

        @PostMapping(value="/login", produces=MediaType.APPLICATION_JSON_VALUE)
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
