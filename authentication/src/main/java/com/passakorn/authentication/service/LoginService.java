package com.passakorn.authentication.service;

import com.passakorn.authentication.dto.LoginRequest;
import com.passakorn.authentication.dto.LoginResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface LoginService {
    LoginResponse login(LoginRequest loginRequest) throws UsernameNotFoundException;
}
