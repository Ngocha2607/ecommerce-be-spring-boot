package com.ngocha.ecommerce.controller;

import com.ngocha.ecommerce.configuration.AppConstants;
import com.ngocha.ecommerce.entity.User;
import com.ngocha.ecommerce.payload.UserDto;
import com.ngocha.ecommerce.payload.UserResponse;
import com.ngocha.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce Application")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public ResponseEntity<UserResponse> getUsers(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_USER_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        UserResponse userResponse = this.userService.getAllUsers(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/public/users/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        UserDto userDto = this.userService.getUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/public/users/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody User user, @PathVariable Long userId) {
        UserDto updatedUser = this.userService.updateUser(userId, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/public/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        String status = this.userService.deleteUser(userId);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
