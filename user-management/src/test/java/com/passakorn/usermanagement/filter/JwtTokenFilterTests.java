package com.passakorn.usermanagement.filter;


import com.passakorn.usermanagement.enumerate.RoleEnum;
import com.passakorn.usermanagement.model.UserModel;
import com.passakorn.usermanagement.repository.UserRepository;
import com.passakorn.usermanagement.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtTokenFilterTests {

    @Mock
    UserRepository userRepository;

    @Mock
    JwtService jwtService;

    @InjectMocks
    JwtTokenFilter jwtTokenFilter;

    String jwtToken = "dasdas.dasdasd.czxczx";
    String username = "admin";
    String notFoundUsername = "notFoundUsername";
    UserModel userModel = UserModel
            .builder()
            .username(username)
            .roleList(Arrays.asList(RoleEnum.ADMIN, RoleEnum.USER))
            .build();

    @Test
    public void noAuthReq() throws Exception {
        // given
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        // when
        this.jwtTokenFilter.doFilter(req, res, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void nonBearerAuth() throws Exception {
        // given
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        req.addHeader("Authorization", "Basic asdasdasda==");

        // when
        this.jwtTokenFilter.doFilter(req, res, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void notFoundUsername() throws Exception {
        // given
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        req.addHeader("Authorization", "Bearer ".concat(jwtToken));
        when(jwtService.extractUsername(matches(jwtToken))).thenReturn(notFoundUsername);
        when(userRepository.findByUsername(matches(notFoundUsername))).thenReturn(Optional.empty());

        // when
        this.jwtTokenFilter.doFilter(req, res, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void foundUsername() throws Exception {
        // given
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        req.addHeader("Authorization", "Bearer ".concat(jwtToken));
        when(jwtService.extractUsername(matches(jwtToken))).thenReturn(username);
        when(userRepository.findByUsername(matches(username))).thenReturn(Optional.of(userModel));

        // when
        this.jwtTokenFilter.doFilter(req, res, chain);

        assertEquals(SecurityContextHolder.getContext().getAuthentication().getName(), username);
    }
}
