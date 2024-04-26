package com.passakorn.authentication.service;

import com.passakorn.authentication.dto.LoginRequest;
import com.passakorn.authentication.dto.LoginResponse;
import com.passakorn.authentication.model.UserModel;
import com.passakorn.authentication.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    private final String LOGIN_FAILED_MESSAGE = "Username or Password is incorrect";

    public LoginServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws UsernameNotFoundException {
        if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            throw new UsernameNotFoundException(this.LOGIN_FAILED_MESSAGE);
        }
        UserModel userModel = this.userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException(this.LOGIN_FAILED_MESSAGE));
        if (!this.passwordEncoder.matches(loginRequest.getPassword(), userModel.getPassword())) {
            throw new UsernameNotFoundException(this.LOGIN_FAILED_MESSAGE);
        }

        return LoginResponse.builder()
                .token(jwtService.GenerateToken(loginRequest.getUsername()))
                .build();
    }
}
