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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService service;

    @GetMapping
    public List<CardDto> getAll() {
        return service.getAll();
    }

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

    @GetMapping("/{id}")
    public CardDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public CardDto create(@RequestBody CreateUpdateCardDto dto) {
        return service.create(dto);
    }

    @PostMapping("/block")
    public void cardBlocking(@RequestBody CardBlockingDto dto) {
        service.cardBlocking(dto);
    }

    @PostMapping("/active")
    public void cardActive(@RequestBody CardActiveDto dto) {
        service.cardActive(dto);
    }

    @PostMapping("/{cardId}/request-block")
    public void requestBlockCard(@PathVariable Long cardId, @AuthenticationPrincipal User user) {
        service.requestBlockCard(cardId, user);
    }

    @PutMapping
    public CardDto update(@RequestBody CreateUpdateCardDto dto) {
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
