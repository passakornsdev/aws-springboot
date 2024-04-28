package com.passakorn.authentication.service;

import com.passakorn.authentication.dto.LoginRequest;
import com.passakorn.authentication.enumerate.RoleEnum;
import com.passakorn.authentication.model.UserModel;
import com.passakorn.authentication.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    LoginServiceImpl loginService;

    private final String username = "test-user";
    private final String password = "password";
    private UserModel userModel = UserModel.builder()
            .username(username)
            .password(password)
            .roleList(Arrays.asList(RoleEnum.ADMIN, RoleEnum.USER))
            .build();

    @Test
    void loginThrowExceptionIfBodyNullOrUsrPwdNull() {
        assertThrows(UsernameNotFoundException.class, () -> loginService.login(null));
        assertThrows(UsernameNotFoundException.class, () -> loginService.login(new LoginRequest()));
    }

    @Test
    void loginThrowExceptionIfUsernameNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> loginService.login(LoginRequest.builder().username("random").build()));
    }

    @Test
    void loginThrowExceptionIfPasswordNotMatch() {
        // given
        when(userRepository.findByUsername(matches(username))).thenReturn(Optional.of(userModel));
        when(passwordEncoder.matches(anyString(), matches(password))).thenReturn(false);

        // then
        assertThrows(UsernameNotFoundException.class, () -> loginService.login(LoginRequest.builder().username(username).password("random").build()));
    }

    @Test
    void loginReturnAccessTokenIfPasswordMatch() {
        // given
        String token = "asdasdasdasdasdasdas";
        when(userRepository.findByUsername(matches(username))).thenReturn(Optional.of(userModel));
        when(passwordEncoder.matches(matches(password), matches(password))).thenReturn(true);
        when(jwtService.generateToken(matches(username))).thenReturn(token);

        // then
        assertEquals(token, loginService.login(LoginRequest.builder().username(username).password(password).build()).getToken());
    }
}
