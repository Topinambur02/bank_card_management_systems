package com.tyrdanov.bank_card_management_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyrdanov.bank_card_management_system.dto.CreateRoleDto;
import com.tyrdanov.bank_card_management_system.dto.RoleDto;
import com.tyrdanov.bank_card_management_system.service.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@Tag(name = "Управление ролями", description = "API для управления ролями")
public class RoleController {
    
    private final RoleService service;

    @Operation(
        summary = "Получить все роли",
        description = "Возвращает список всех зарегистрированных ролей"
    )
    @GetMapping
    public List<RoleDto> getAll() {
        return service.getAll();
    }

    @Operation(
        summary = "Получить роль по ID",
        description = "Возвращает роль по указанному идентификатору"
    )
    @GetMapping("/{id}")
    public RoleDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(
        summary = "Создать новую роль",
        description = "Создает новую роль с указанными параметрами"
    )
    @PostMapping
    public RoleDto create(@RequestBody CreateRoleDto dto) {
        return service.create(dto);
    }

    @Operation(
        summary = "Обновить роль",
        description = "Обновляет данные существующей роли"
    )
    @PutMapping
    public RoleDto update(@RequestBody RoleDto dto) {
        return service.update(dto);
    }

    @Operation(
        summary = "Удалить роль",
        description = "Удаляет роль по указанному идентификатору"
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
