package com.ngocha.ecommerce.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginCredentials {
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String password;
}
