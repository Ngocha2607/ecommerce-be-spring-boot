package com.ngocha.ecommerce.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce Application")
public class UserController {

    @GetMapping("/admin/users")
    public ResponseEntity<String> getUsers() {

        return new ResponseEntity<String>("Test", HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
