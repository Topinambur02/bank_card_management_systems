package com.tyrdanov.bank_card_management_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tyrdanov.bank_card_management_system.model.User;
import com.tyrdanov.bank_card_management_system.repository.UserRepository;
import com.tyrdanov.bank_card_management_system.service.UserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_WhenUserExists_ReturnsUserDetails() {
        final var email = "test@example.com";
        final var mockUser = User.builder().build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        final var result = userDetailsService.loadUserByUsername(email);

        assertNotNull(result);
        assertEquals(mockUser, result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void loadUserByUsername_WhenUserNotExists_ThrowsException() {
        final var email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        final var exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email));

        assertEquals("User not found with login: " + email, exception.getMessage());
        verify(userRepository, times(1)).findByEmail(email);
    }

}
