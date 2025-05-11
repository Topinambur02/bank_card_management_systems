package com.tyrdanov.bank_card_management_system.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.MediaType;

import com.tyrdanov.bank_card_management_system.dto.SignInDto;
import com.tyrdanov.bank_card_management_system.dto.SignUpDto;
import com.tyrdanov.bank_card_management_system.dto.UserDto;
import com.tyrdanov.bank_card_management_system.dto.response.TokenResponse;
import com.tyrdanov.bank_card_management_system.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Методы для авторизации", description = "Методы для авторизации и регистрации пользователей")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя", description = "Получает DTO пользователя и создает нового пользователя")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Пользователь создан", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SignUpDto.class))))
    public UserDto register(@Valid @RequestBody SignUpDto dto) {
        return service.register(dto);
    }

    @PostMapping("/login")
    @Operation(summary = "Авторизация пользователя", description = "Получает DTO пользователя и авторизует пользователя")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Пользователь авторизован", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TokenResponse.class))))
    public TokenResponse login(@Valid @RequestBody SignInDto dto) {
        return service.login(dto);
    }

}
