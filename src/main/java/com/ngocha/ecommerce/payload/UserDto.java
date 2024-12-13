package com.ngocha.ecommerce.payload;


import com.ngocha.ecommerce.entity.Role;
import lombok.Data;

@Data
public class UserDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
//    private String password;
    private Role role;
}
