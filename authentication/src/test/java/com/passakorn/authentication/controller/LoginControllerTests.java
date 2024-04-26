package com.passakorn.authentication.controller;

import com.passakorn.authentication.dto.LoginRequest;
import com.passakorn.authentication.dto.LoginResponse;
import com.passakorn.authentication.service.LoginService;
import com.passakorn.authentication.service.LoginServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTests {

    @Mock
    LoginService loginService;

    @InjectMocks
    LoginController loginController;

    @Test
    void postLoginThrowUsername() {
        // given
        when(loginService.login(isNull())).thenThrow(UsernameNotFoundException.class);

        // then
        assertThrows(UsernameNotFoundException.class, () -> loginController.postLogin(null));
    }

    @Test
    void postLoginReturnToken() {
        // given
        LoginResponse loginResponse = LoginResponse.builder().build();
        when(loginService.login(any())).thenReturn(loginResponse);

        // then
        assertEquals(loginController.postLogin(new LoginRequest()), loginResponse);
    }
}
