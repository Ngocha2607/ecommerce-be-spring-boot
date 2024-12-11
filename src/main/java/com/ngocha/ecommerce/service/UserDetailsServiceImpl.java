package com.ngocha.ecommerce.service;

import com.ngocha.ecommerce.configuration.UserInfoConfig;
import com.ngocha.ecommerce.entity.User;
import com.ngocha.ecommerce.exception.ResourceNotFoundException;
import com.ngocha.ecommerce.exception.UserNotFoundException;
import com.ngocha.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByEmail(username);

        return user.map(UserInfoConfig::new).orElseThrow(() -> new ResourceNotFoundException("User", "email", username));
    }
}
