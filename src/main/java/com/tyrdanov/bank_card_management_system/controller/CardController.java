package com.tyrdanov.bank_card_management_system.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tyrdanov.bank_card_management_system.dto.CardActiveDto;
import com.tyrdanov.bank_card_management_system.dto.CardBlockingDto;
import com.tyrdanov.bank_card_management_system.dto.CardDto;
import com.tyrdanov.bank_card_management_system.dto.CreateUpdateCardDto;
import com.tyrdanov.bank_card_management_system.dto.request.CardFilterRequest;
import com.tyrdanov.bank_card_management_system.enums.Status;
import com.tyrdanov.bank_card_management_system.model.User;
import com.tyrdanov.bank_card_management_system.service.CardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
@Tag(name = "Управление картами", description = "API для управления банковскими картами")
public class CardController {

    private final CardService service;

    @Operation(summary = "Получить все карты", description = "Возвращает список всех карт без фильтрации")
    @GetMapping
    public List<CardDto> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Поиск карт с фильтрацией", description = "Возвращает отфильтрованный и paginated список карт")
    @Parameter(name = "status", description = "Статус карты (ACTIVE, BLOCKED и т.д.)")
    @Parameter(name = "balance", description = "Баланс для фильтрации")
    @Parameter(name = "cardNumber", description = "Номер карты (частичное совпадение)")
    @Parameter(name = "validityPeriod", description = "Дата действительности в формате yyyy-MM-dd")
    @Parameter(name = "page", description = "Номер страницы (начиная с 0)", example = "0")
    @Parameter(name = "size", description = "Размер страницы", example = "20")
    @Parameter(name = "sort", description = "Поле для сортировки (формат: field,asc|desc)", example = "id,asc")
    @GetMapping("/filter")
    public List<CardDto> getAllWithFilterAndPageable(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Double balance,
            @RequestParam(required = false) String cardNumber,
            @RequestParam(required = false) LocalDate validityPeriod,
            @PageableDefault(sort = "id") Pageable pageable,
            @AuthenticationPrincipal User user) {

        final var request = CardFilterRequest
                .builder()
                .status(status)
                .balance(balance)
                .cardNumber(cardNumber)
                .validityPeriod(validityPeriod)
                .userId(user.getId())
                .build();

        return service.getAllWithFilterAndPageable(request, pageable);
    }

    @Operation(summary = "Получить карту по ID")
    @GetMapping("/{id}")
    public CardDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(summary = "Создать новую карту")
    @PostMapping
    public CardDto create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Данные для создания карты", 
                required = true
            ) 
            @RequestBody CreateUpdateCardDto dto) {
        return service.create(dto);
    }

    @Operation(summary = "Блокировка карты")
    @PostMapping("/block")
    public void cardBlocking(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Данные для блокировки карты",
                required = true
            )
            @RequestBody CardBlockingDto dto) {
        service.cardBlocking(dto);
    }

    @Operation(summary = "Активация карты")
    @PostMapping("/active")
    public void cardActive(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Данные для активации карты",
                required = true
            )
            @RequestBody CardActiveDto dto) {
        service.cardActive(dto);
    }

    @Operation(summary = "Запрос на блокировку карты")
    @PostMapping("/{cardId}/request-block")
    public void requestBlockCard(
        @Parameter(description = "ID карты", required = true, example = "123")
        @PathVariable Long cardId, 
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        service.requestBlockCard(cardId, user);
    }

    @Operation(summary = "Обновить данные карты")
    @PutMapping
    public CardDto update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Обновленные данные карты",
                required = true
            )    
            @RequestBody CreateUpdateCardDto dto) {
        return service.update(dto);
    }

    @Operation(summary = "Удалить карту по ID")
    @DeleteMapping("/{id}")
    public void delete(
        @Parameter(description = "ID карты для удаления", required = true, example = "123")    
        @PathVariable Long id) {
        service.delete(id);
    }

}
