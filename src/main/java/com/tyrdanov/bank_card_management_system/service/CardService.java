package com.tyrdanov.bank_card_management_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tyrdanov.bank_card_management_system.dto.CardDto;
import com.tyrdanov.bank_card_management_system.exception.ResourceNotFoundException;
import com.tyrdanov.bank_card_management_system.mapper.CardMapper;
import com.tyrdanov.bank_card_management_system.repository.CardRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardMapper mapper;
    private final CardRepository repository;

    public List<CardDto> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public CardDto getById(Long id) {
        final var card = repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Card not found")
        );

        return mapper.toDto(card);
    }

    public CardDto create(CardDto dto) {
        final var card = mapper.toModel(dto);
        final var createdCard = repository.save(card);

        return mapper.toDto(createdCard);
    }

    @Transactional
    public CardDto update(CardDto dto) {
        final var id = dto.getId();
        final var updatedCard = repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Card not found")
        );

        mapper.update(dto, updatedCard);

        return mapper.toDto(updatedCard);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
