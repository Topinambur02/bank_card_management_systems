package com.tyrdanov.bank_card_management_system.service;

import java.util.List;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyrdanov.bank_card_management_system.dto.CardActiveDto;
import com.tyrdanov.bank_card_management_system.dto.CardBlockingDto;
import com.tyrdanov.bank_card_management_system.dto.CardDto;
import com.tyrdanov.bank_card_management_system.dto.CreateUpdateCardDto;
import com.tyrdanov.bank_card_management_system.dto.request.CardFilterRequest;
import com.tyrdanov.bank_card_management_system.enums.Status;
import com.tyrdanov.bank_card_management_system.exception.ResourceNotFoundException;
import com.tyrdanov.bank_card_management_system.mapper.CardMapper;
import com.tyrdanov.bank_card_management_system.model.Card;
import com.tyrdanov.bank_card_management_system.model.User;
import com.tyrdanov.bank_card_management_system.repository.CardRepository;
import com.tyrdanov.bank_card_management_system.repository.UserRepository;
import com.tyrdanov.bank_card_management_system.specification.CardSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardMapper mapper;
    private final CardRepository repository;
    private final UserRepository userRepository;
    private final CardSpecification specification;
    private final PooledPBEStringEncryptor encryptor;

    public List<CardDto> getAll() {
        return repository
                .findAll()
                .stream()
                .map(this::createCardWithDecryptedNumber)
                .map(mapper::toDto)
                .toList();
    }

    public List<CardDto> getAllWithFilterAndPageable(CardFilterRequest request, Pageable pageable) {
        final var filter = specification.filter(request);

        return repository
                .findAll(filter, pageable)
                .stream()
                .map(this::createCardWithDecryptedNumber)
                .map(mapper::toDto)
                .toList();
    }

    public CardDto getById(Long id) {
        final var card = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        final var cardWithDecryptedNumber = createCardWithDecryptedNumber(card);

        return mapper.toDto(cardWithDecryptedNumber);
    }

    public CardDto create(CreateUpdateCardDto dto) {
        final var cardNumber = dto.getCardNumber();
        final var userId = dto.getUserId();
        final var encryptedCardNumber = encryptor.encrypt(cardNumber);
        final var user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        final var card = mapper.toModel(dto, user);

        card.setCardNumber(encryptedCardNumber);

        final var createdCard = repository.save(card);

        return mapper.toDto(createdCard);
    }

    @Transactional
    public CardDto update(CreateUpdateCardDto dto) {
        final var id = dto.getId();
        final var userId = dto.getUserId();
        final var cardNumber = dto.getCardNumber();
        final var encryptedCardNumber = encryptor.encrypt(cardNumber);
        final var updatedCard = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
        final var user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        mapper.update(dto, user, updatedCard);
        updatedCard.setCardNumber(encryptedCardNumber);

        return mapper.toDto(updatedCard);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void cardBlocking(CardBlockingDto dto) {
        final var cardId = dto.getCardId();
        final var card = repository
                .findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        card.setStatus(Status.BLOCKED);
    }

    @Transactional
    public void cardActive(CardActiveDto dto) {
        final var cardId = dto.getCardId();
        final var card = repository
                .findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        card.setStatus(Status.ACTIVE);
    }

    @Transactional
    public void requestBlockCard(Long cardId, User user) {
        final var currentUserId = user.getId();
        final var card = repository
                .findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
        final var userId = card.getUser().getId();

        if (!userId.equals(currentUserId)) {
            throw new AccessDeniedException("You don't have access to this card");
        }

        card.setStatus(Status.BLOCKED);
    }

    private Card createCardWithDecryptedNumber(Card card) {
        final var cardNumber = card.getCardNumber();
        final var decryptedCardNumber = encryptor.decrypt(cardNumber);

        card.setCardNumber(decryptedCardNumber);

        return card;
    }

}
