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

import com.tyrdanov.bank_card_management_system.dto.CardDto;
import com.tyrdanov.bank_card_management_system.dto.CreateUpdateCardDto;
import com.tyrdanov.bank_card_management_system.service.CardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    
    private final CardService service;

    @GetMapping
    public List<CardDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CardDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public CardDto create(@RequestBody CreateUpdateCardDto dto) {
        return service.create(dto);
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
