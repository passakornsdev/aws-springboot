package com.passakorn.usermanagement.controller;

import com.passakorn.usermanagement.model.UserModel;
import com.passakorn.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/{username}")
    public UserModel getUserById(@PathVariable String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    @PostMapping
    public ResponseEntity<UserModel> postAddUser(final @RequestBody UserModel user) {
        return ResponseEntity.ok(this.userRepository.save(user));
    }
}
