package com.ngocha.ecommerce.payload;

import lombok.Data;

@Data
public class JWTAuthResponse {
    private String token;
    private UserDto user;
}
