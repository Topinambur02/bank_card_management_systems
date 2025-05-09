package com.tyrdanov.bank_card_management_system.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tyrdanov.bank_card_management_system.dto.SignInDto;
import com.tyrdanov.bank_card_management_system.dto.SignUpDto;
import com.tyrdanov.bank_card_management_system.dto.UserDto;
import com.tyrdanov.bank_card_management_system.dto.response.TokenResponse;
import com.tyrdanov.bank_card_management_system.exception.UserAlreadyExistsException;
import com.tyrdanov.bank_card_management_system.mapper.UserMapper;
import com.tyrdanov.bank_card_management_system.model.User;
import com.tyrdanov.bank_card_management_system.repository.UserRepository;
import com.tyrdanov.bank_card_management_system.util.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;
    private final UserMapper mapper;
    private final AuthenticationManager manager;
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    public TokenResponse login(SignInDto dto) {
        final var email = dto.getEmail();
        final var password = dto.getPassword();
        final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        final var authorization = manager.authenticate(usernamePasswordAuthenticationToken);
        final var userDetails = (User) authorization.getPrincipal();
        final var token = jwtUtils.generateToken(userDetails);

        return new TokenResponse(token);
    }

    public UserDto register(SignUpDto dto) {
        final var email = dto.getEmail();
        final var password = dto.getPassword();
        final var encryptedPassword = encoder.encode(password);
        final var isExist = userRepository.existsByEmail(email);

        if (isExist) {
            throw new UserAlreadyExistsException("User already exists");
        }

        final var user = User
                .builder()
                .email(email)
                .password(encryptedPassword)
                .build();

        final var savedUser = userRepository.save(user);

        return mapper.toDto(savedUser);
    }

}
