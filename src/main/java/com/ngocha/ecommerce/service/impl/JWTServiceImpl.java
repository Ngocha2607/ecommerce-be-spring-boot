package com.ngocha.ecommerce.service.impl;

import com.ngocha.ecommerce.configuration.AppConstants;
import com.ngocha.ecommerce.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + AppConstants.JWT_TOKEN_VALIDITY))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extracts a specific claim from the JWT token.
    // claimResolver A function to extract the claim.
    // return-> The value of the specified claim.
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        // Extract the specified claim using the provided function
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    //Extracts all claims from the JWT token.
    //return-> Claims object containing all claims.
    private Claims extractAllClaims(String token) {
        // Parse and return all claims from the token
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] key = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
        return Keys.hmacShaKeyFor(key);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    // Extracts the expiration date from the JWT token.
    //@return The expiration date of the token.
    public Date extractExpiration(String token) {
        // Extract and return the expiration claim from the token
        return extractClaim(token, Claims::getExpiration);
    }

    //Validates the JWT token against the UserDetails.
    //return-> True if the token is valid, false otherwise.
    //Checks if the JWT token is expired.
    //return-> True if the token is expired, false otherwise.
    public Boolean isTokenExpired(String token) {
        // Check if the token's expiration time is before the current time
        return extractExpiration(token).before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        // Extract username from token and check if it matches UserDetails' username
         String userName = extractUserName(token);
        // Also check if the token is expired
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
