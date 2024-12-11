package com.ngocha.ecommerce.service;

import com.ngocha.ecommerce.payload.UserDto;
import com.ngocha.ecommerce.payload.UserResponse;

public interface UserService {
    UserDto registerUser(UserDto userDto);
    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    UserDto getUserById(Long userId);
    UserDto updateUser(Long userId, UserDto userDto);
    String deleteUser(Long userId);
}
