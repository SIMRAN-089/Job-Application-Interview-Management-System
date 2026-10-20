package com.simran.job_management.service;

import com.simran.job_management.entity.User;
import com.simran.job_management.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Test
    void registerRejectsDuplicateEmail() {
        UserRepository repository = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        when(repository.existsByEmail("same@example.com")).thenReturn(true);
        AuthService service = new AuthService(repository, encoder, null, null, null);
        User user = new User();
        user.setEmail("same@example.com");
        assertThrows(IllegalArgumentException.class, () -> service.register(user));
        verify(repository, never()).save(any(User.class));
    }
}
