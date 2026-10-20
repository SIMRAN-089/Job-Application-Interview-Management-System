package com.simran.job_management.service;

import com.simran.job_management.dto.AuthRequest;
import com.simran.job_management.dto.AuthResponse;
import com.simran.job_management.entity.User;
import com.simran.job_management.repository.UserRepository;
import com.simran.job_management.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthService(UserRepository users, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService, JwtService jwtService) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Transactional
    public User register(User user) {
        if (users.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("An account with this email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return users.save(user);
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        UserDetails details = userDetailsService.loadUserByUsername(request.email());
        return new AuthResponse(jwtService.generateToken(details), "Bearer");
    }
}
