package com.ngocha.ecommerce.payload.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;
}
