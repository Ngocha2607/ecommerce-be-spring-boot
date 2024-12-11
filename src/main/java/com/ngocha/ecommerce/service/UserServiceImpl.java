package com.ngocha.ecommerce.service;

import com.ngocha.ecommerce.payload.UserResponse;
import com.ngocha.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UserServiceImpl  {
    @Autowired
    private UserRepository userRepository;

}
