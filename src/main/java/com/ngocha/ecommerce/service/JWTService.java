package com.ngocha.ecommerce.service;

import com.ngocha.ecommerce.payload.JWTAuthResponse;
import com.ngocha.ecommerce.payload.request.RefreshTokenRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface JWTService {
    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);

    Boolean validateToken(String token, UserDetails userDetails);

    JWTAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
