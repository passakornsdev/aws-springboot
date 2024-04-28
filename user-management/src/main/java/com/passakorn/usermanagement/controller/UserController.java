package com.passakorn.usermanagement.controller;

import com.passakorn.usermanagement.model.UserModel;
import com.passakorn.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping
    public Object getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping
    public ResponseEntity<UserModel> postAddUser(final @RequestBody UserModel user) {
        return ResponseEntity.ok(this.userRepository.save(user));
    }
}
