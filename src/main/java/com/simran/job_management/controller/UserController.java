
package com.simran.job_management.controller;

import com.simran.job_management.dto.UserDTO;
import com.simran.job_management.dto.UserRequestDTO;
import com.simran.job_management.entity.User;
import com.simran.job_management.mapper.UserMapper;
import com.simran.job_management.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);

        return user.map(UserMapper::toDTO).orElse(null);
    }

    // Create a recruiter or candidate
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserRequestDTO dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        User savedUser = userService.saveUser(user);

        return UserMapper.toDTO(savedUser);
    }
}
