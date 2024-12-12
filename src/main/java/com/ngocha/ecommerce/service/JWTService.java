package com.ngocha.ecommerce.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public interface JWTService {
    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    Boolean validateToken(String token, UserDetails userDetails);

}
