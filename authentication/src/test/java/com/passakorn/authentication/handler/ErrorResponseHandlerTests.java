package com.passakorn.authentication.handler;

import com.google.gson.Gson;
import com.passakorn.authentication.dto.ErrorResponseMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(MockController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ErrorResponseHandlerTests {
    static String errorMessage = "test exception";
    static String authErrorMessage = "auth exception";



    @Autowired
    MockMvc mockMvc;

    @Test
    void generalExceptionResponseTest() throws Exception {
        mockMvc.perform(get("/test/exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(new Gson().toJson(ErrorResponseMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(ErrorResponseHandlerTests.errorMessage).build())))
                .andReturn();
    }

    @Test
    void unAuthResponseTest() throws Exception {
        mockMvc.perform(get("/test/auth-exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(new Gson().toJson(ErrorResponseMessage.builder().status(HttpStatus.UNAUTHORIZED).message(ErrorResponseHandlerTests.authErrorMessage).build())))
                .andReturn();
    }
}

@Controller
@RequestMapping("/test")
class MockController {
    @GetMapping("/exception")
    void exceptionApi() throws Exception {
        throw new Exception(ErrorResponseHandlerTests.errorMessage);
    }
    @GetMapping("/auth-exception")
    void authExceptionApi() throws AuthenticationException {
        throw new UsernameNotFoundException(ErrorResponseHandlerTests.authErrorMessage);
    }
}
