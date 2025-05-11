package com.tyrdanov.bank_card_management_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tyrdanov.bank_card_management_system.dto.SignInDto;
import com.tyrdanov.bank_card_management_system.dto.SignUpDto;
import com.tyrdanov.bank_card_management_system.dto.UserDto;
import com.tyrdanov.bank_card_management_system.exception.ResourceNotFoundException;
import com.tyrdanov.bank_card_management_system.exception.UserAlreadyExistsException;
import com.tyrdanov.bank_card_management_system.mapper.UserMapper;
import com.tyrdanov.bank_card_management_system.model.Role;
import com.tyrdanov.bank_card_management_system.model.User;
import com.tyrdanov.bank_card_management_system.repository.RoleRepository;
import com.tyrdanov.bank_card_management_system.repository.UserRepository;
import com.tyrdanov.bank_card_management_system.service.AuthService;
import com.tyrdanov.bank_card_management_system.util.JwtUtils;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_Success() {
        final var email = "test@example.com";
        final var password = "password";
        final var token = "jwt.token";
        final var signInDto = SignInDto
                .builder()
                .email(email)
                .password(password)
                .build();
        final var userDetails = User
                .builder()
                .email(email)
                .password(password)
                .build();

        final var authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateToken(userDetails)).thenReturn(token);

        final var response = authService.login(signInDto);

        assertNotNull(response);
        assertEquals(token, response.getToken());
        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    @Test
    void login_InvalidCredentials_ThrowsException() {
        final var email = "wrong@example.com";
        final var password = "wrong";
        final var signInDto = SignInDto
                .builder()
                .email(email)
                .password(password)
                .build();

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(BadCredentialsException.class, () -> authService.login(signInDto));
    }

    @Test
    void register_UserAlreadyExists_ThrowsException() {
        final var email = "exist@example.com";
        final var password = "pass";
        final var signUpDto = SignUpDto
                .builder()
                .email(email)
                .password(password)
                .build();

        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> authService.register(signUpDto));
    }

    @Test
    void register_RoleNotFound_ThrowsException() {
        final var email = "new@example.com";
        final var password = "pass";
        final var roleId = 99L;
        final var signUpDto = SignUpDto
                .builder()
                .email(email)
                .password(password)
                .roleId(roleId)
                .build();

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authService.register(signUpDto));
    }

    @Test
    void register_DefaultRole_Success() {
        final var email = "new@example.com";
        final var password = "pass";
        final var signUpDto = SignUpDto
                .builder()
                .email(email)
                .password(password)
                .build();
        final var defaultRole = Role
                .builder()
                .id(1L)
                .name("ROLE_USER")
                .build();
        final var user = User
                .builder()
                .email("new@example.com")
                .password("encodedPass")
                .role(defaultRole)
                .build();
        final var expectedDto = UserDto
                .builder()
                .id(1L)
                .email("new@example.com")
                .build();

        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(defaultRole));
        when(bCryptPasswordEncoder.encode("pass")).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(expectedDto);

        final var result = authService.register(signUpDto);

        assertNotNull(result);
        assertEquals(expectedDto.getEmail(), result.getEmail());
        verify(userRepository).save(argThat(u -> u.getEmail().equals("new@example.com") &&
                u.getPassword().equals("encodedPass") &&
                u.getRole().equals(defaultRole)));
    }

    @Test
    void register_CustomRole_Success() {
        final var roleId = 2L;
        final var signUpDto = SignUpDto
                .builder()
                .email("user@test.com")
                .password("pass")
                .roleId(roleId)
                .build();
        final var customRole = Role
                .builder()
                .id(roleId)
                .name("ROLE_ADMIN")
                .build();
        final var user = User
                .builder()
                .email("user@test.com")
                .password("encodedPass")
                .role(customRole)
                .build();
        final var expectedDto = UserDto
                .builder()
                .id(1L)
                .email("user@test.com")
                .build();

        when(userRepository.existsByEmail("user@test.com")).thenReturn(false);
        when(roleRepository.findById(roleId))
                .thenReturn(Optional.of(customRole));
        when(bCryptPasswordEncoder.encode("pass")).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(expectedDto);

        final var result = authService.register(signUpDto);

        assertNotNull(result);
        assertEquals(expectedDto.getEmail(), result.getEmail());
        verify(roleRepository).findById(roleId);
        verify(roleRepository, never()).findByName(anyString());
    }

}
