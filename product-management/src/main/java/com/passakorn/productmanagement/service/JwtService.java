package com.passakorn.productmanagement.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    Boolean validateToken(String token, UserDetails userDetails);
    String extractUsername(String token);
}
