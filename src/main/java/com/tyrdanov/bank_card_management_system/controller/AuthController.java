package com.tyrdanov.bank_card_management_system.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyrdanov.bank_card_management_system.dto.SignInDto;
import com.tyrdanov.bank_card_management_system.dto.SignUpDto;
import com.tyrdanov.bank_card_management_system.dto.UserDto;
import com.tyrdanov.bank_card_management_system.dto.response.TokenResponse;
import com.tyrdanov.bank_card_management_system.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService service;

    @PostMapping("/register")
    public UserDto register(@RequestBody SignUpDto dto) {
        return service.register(dto);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody SignInDto dto) {
        return service.login(dto);
    }

}
