package com.ngocha.ecommerce.service;

import com.ngocha.ecommerce.entity.User;
import com.ngocha.ecommerce.payload.UserDto;
import com.ngocha.ecommerce.payload.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDto registerUser(User user);
    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    UserDto getUserById(Long userId);
    UserDto updateUser(Long userId, User user);
    String deleteUser(Long userId);
    UserDetailsService userDetailsService();
    UserDto getUserByEmail(String email);
}
