package com.ngocha.ecommerce.service.impl;

import com.ngocha.ecommerce.entity.Role;
import com.ngocha.ecommerce.entity.User;
import com.ngocha.ecommerce.exception.APIException;
import com.ngocha.ecommerce.exception.ResourceNotFoundException;
import com.ngocha.ecommerce.payload.UserDto;
import com.ngocha.ecommerce.payload.UserResponse;
import com.ngocha.ecommerce.repository.UserRepository;
import com.ngocha.ecommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
@Autowired
    private PasswordEncoder passwordEncoder;
@Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    @Override
    public UserDto getUserByEmail(String email) throws UsernameNotFoundException {
        return modelMapper.map(
                userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found")),
                UserDto.class
        );
    }

    public UserDto registerUser(User user) {

        user.setRole(Role.USER.name());

        User registeredUser = userRepository.save(user);

        return modelMapper.map(registeredUser, UserDto.class);

    }


    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<User> pageUsers = userRepository.findAll(pageDetails);

        List<User> users = pageUsers.getContent();

        if(users.isEmpty()) {
            throw new APIException("No User Exist!");
        }

        List<UserDto> userDtos = users.stream().map(user -> {
            UserDto dto = modelMapper.map(user, UserDto.class);
            return dto;
        }).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();

        userResponse.setContent(userDtos);
        userResponse.setPageNumber(pageUsers.getNumber());
        userResponse.setPageSize(pageUsers.getSize());
        userResponse.setTotalElements(pageUsers.getTotalElements());
        userResponse.setTotalPages(pageUsers.getTotalPages());
        userResponse.setLastPage(pageUsers.isLast());

        return userResponse;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(Long userId, User user) {
        User existUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        String encodedPass = passwordEncoder.encode(user.getPassword());
        existUser.setFirstName(user.getFirstName());
        existUser.setLastName(user.getLastName());
        existUser.setMobileNumber(user.getMobileNumber());
        existUser.setEmail(user.getEmail());
        existUser.setPassword(encodedPass);

        return modelMapper.map(existUser, UserDto.class);
    }

    @Override
    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        userRepository.delete(user);
        return "User with userId" + userId + " deleted successfully!";
    }
}
