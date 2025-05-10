package com.tyrdanov.bank_card_management_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyrdanov.bank_card_management_system.dto.UpdateUserDto;
import com.tyrdanov.bank_card_management_system.dto.UserDto;
import com.tyrdanov.bank_card_management_system.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Управление пользователями", description = "API для управления пользователями")
public class UserController {
    
    private final UserService service;

     @Operation(
        summary = "Получить всех пользователей",
        description = "Возвращает список всех зарегистрированных пользователей"
    )
    @GetMapping
    public List<UserDto> getAll() {
        return service.getAll();
    }

    @Operation(
        summary = "Получить пользователя по ID",
        description = "Возвращает подробную информацию о конкретном пользователе"
    )
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(
        summary = "Обновить данные пользователя",
        description = "Обновляет информацию о существующем пользователе"
    )
    @PutMapping
    public UserDto update(@RequestBody UpdateUserDto dto) {
        return service.update(dto);
    }

    @Operation(
        summary = "Удалить пользователя",
        description = "Удаляет пользователя по указанному идентификатору"
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
