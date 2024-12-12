package com.ngocha.ecommerce.service;

import com.ngocha.ecommerce.payload.UserDto;
import com.ngocha.ecommerce.payload.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDto registerUser(UserDto userDto);
    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    UserDto getUserById(Long userId);
    UserDto updateUser(Long userId, UserDto userDto);
    String deleteUser(Long userId);
    UserDetailsService userDetailsService();
}
