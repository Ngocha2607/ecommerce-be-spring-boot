package com.ngocha.ecommerce.controller;

import com.ngocha.ecommerce.entity.User;
import com.ngocha.ecommerce.exception.UserNotFoundException;
import com.ngocha.ecommerce.payload.JWTAuthResponse;
import com.ngocha.ecommerce.payload.LoginCredentials;
import com.ngocha.ecommerce.payload.UserDto;
import com.ngocha.ecommerce.payload.request.RefreshTokenRequest;
import com.ngocha.ecommerce.service.JWTService;
import com.ngocha.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerHandler(@Valid @RequestBody User user) throws UserNotFoundException
    {

        String encodedPass = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPass);

        UserDto userDto = userService.registerUser(user);

//        String token = jwtService.generateToken(userService.userDetailsService());

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public JWTAuthResponse loginHandler(@Valid @RequestBody LoginCredentials credentials) {
        UsernamePasswordAuthenticationToken authCredentials = new UsernamePasswordAuthenticationToken(
                credentials.getEmail(), credentials.getPassword()
        );

        authenticationManager.authenticate(authCredentials);

    UserDto userDto = userService.getUserByEmail(credentials.getEmail());
    User user = modelMapper.map(userDto, User.class);
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

    JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();

    jwtAuthResponse.setUser(userDto);
    jwtAuthResponse.setToken(token);
    jwtAuthResponse.setRefreshToken(refreshToken);

    return jwtAuthResponse;
    }

    @PostMapping("/refresh")
    public JWTAuthResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        return jwtService.refreshToken(refreshTokenRequest);
    }
}
