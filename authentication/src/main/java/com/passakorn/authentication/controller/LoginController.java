package com.passakorn.authentication.controller;

import com.passakorn.authentication.dto.LoginRequest;
import com.passakorn.authentication.dto.LoginResponse;
import com.passakorn.authentication.service.LoginService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public LoginResponse postLogin(@RequestBody LoginRequest body) {
        return this.loginService.login(body);
    }
}
