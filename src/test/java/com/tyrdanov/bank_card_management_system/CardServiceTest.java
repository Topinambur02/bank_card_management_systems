package com.tyrdanov.bank_card_management_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;

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
import com.tyrdanov.bank_card_management_system.service.CardService;
import com.tyrdanov.bank_card_management_system.specification.CardSpecification;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    
    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CardMapper cardMapper;

    @Mock
    private CardSpecification cardSpecification;

    @Mock
    private PooledPBEStringEncryptor encryptor;

    @InjectMocks
    private CardService cardService;

    private final Long CARD_ID = 1L;
    private final Long USER_ID = 1L;
    private final String ENCRYPTED_NUMBER = "encrypted";
    private final String DECRYPTED_NUMBER = "1234567812345678";

    @Test
    void getAll_ShouldReturnListOfDecryptedCards() {
        final var card = mock(Card.class);

        when(card.getCardNumber()).thenReturn(ENCRYPTED_NUMBER);
        when(cardRepository.findAll()).thenReturn(List.of(card));
        when(encryptor.decrypt(ENCRYPTED_NUMBER)).thenReturn(DECRYPTED_NUMBER);
        when(cardMapper.toDto(any(Card.class))).thenReturn(new CardDto());

        final var result = cardService.getAll();

        assertEquals(1, result.size());
        verify(encryptor).decrypt(ENCRYPTED_NUMBER);
        verify(card).setCardNumber(DECRYPTED_NUMBER);
        verify(cardMapper).toDto(card);
    }

    @Test
    void getAllWithFilterAndPageable_ShouldApplyFilterAndReturnResults() {
        final var request = CardFilterRequest.builder().build();
        final var pageable = Pageable.unpaged();
        final var card = mock(Card.class);

        when(card.getCardNumber()).thenReturn(ENCRYPTED_NUMBER);
        
        final var page = new PageImpl<>(List.of(card));

        when(cardSpecification.filter(request)).thenReturn((Specification<Card>) (root, query, cb) -> null);
        when(cardRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(encryptor.decrypt(ENCRYPTED_NUMBER)).thenReturn(DECRYPTED_NUMBER);
        when(cardMapper.toDto(any(Card.class))).thenReturn(new CardDto());

        final var result = cardService.getAllWithFilterAndPageable(request, pageable);

        assertFalse(result.isEmpty());
        verify(card).setCardNumber(DECRYPTED_NUMBER);
        verify(cardRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void getById_WhenCardExists_ShouldReturnDecryptedCard() {
        final var card = new Card();

        card.setCardNumber(ENCRYPTED_NUMBER);

        when(cardRepository.findById(CARD_ID)).thenReturn(Optional.of(card));
        when(encryptor.decrypt(ENCRYPTED_NUMBER)).thenReturn(DECRYPTED_NUMBER);
        when(cardMapper.toDto(any())).thenReturn(new CardDto());

        final var result = cardService.getById(CARD_ID);

        assertNotNull(result);
        verify(cardRepository).findById(CARD_ID);
    }

    @Test
    void getById_WhenCardNotExists_ShouldThrowException() {
        when(cardRepository.findById(CARD_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cardService.getById(CARD_ID));
    }

    @Test
    void create_ShouldEncryptNumberAndSaveCard() {
        final var dto = new CreateUpdateCardDto();
        dto.setCardNumber(DECRYPTED_NUMBER);
        dto.setUserId(USER_ID);

        final var user = new User();
        final var card = mock(Card.class);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(encryptor.encrypt(DECRYPTED_NUMBER)).thenReturn(ENCRYPTED_NUMBER);
        when(cardMapper.toModel(dto, user)).thenReturn(card);
        when(cardRepository.save(card)).thenReturn(card);
        when(cardMapper.toDto(card)).thenReturn(new CardDto());

        final var result = cardService.create(dto);

        assertNotNull(result);
        verify(encryptor).encrypt(DECRYPTED_NUMBER);
        verify(card).setCardNumber(ENCRYPTED_NUMBER);
    }

    @Test
    void create_WhenUserNotExists_ShouldThrowException() {
        final var dto = new CreateUpdateCardDto();

        dto.setUserId(USER_ID);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cardService.create(dto));
    }

    @Test
    void update_ShouldUpdateCardWithEncryptedNumber() {
        final var dto = new CreateUpdateCardDto();
        dto.setId(CARD_ID);
        dto.setUserId(USER_ID);
        dto.setCardNumber(DECRYPTED_NUMBER);

        final var existingCard = mock(Card.class);
        final var user = new User();

        when(cardRepository.findById(CARD_ID)).thenReturn(Optional.of(existingCard));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(encryptor.encrypt(DECRYPTED_NUMBER)).thenReturn(ENCRYPTED_NUMBER);
        when(cardMapper.toDto(existingCard)).thenReturn(new CardDto());

        final var result = cardService.update(dto);

        assertNotNull(result);
        verify(cardMapper).update(eq(dto), eq(user), eq(existingCard));
        verify(existingCard).setCardNumber(ENCRYPTED_NUMBER);
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        cardService.delete(CARD_ID);

        verify(cardRepository).deleteById(CARD_ID);
    }

    @Test
    void cardBlocking_ShouldSetStatusToBlocked() {
        final var dto = CardBlockingDto.builder().cardId(CARD_ID).build();
        final var card = new Card();

        when(cardRepository.findById(CARD_ID)).thenReturn(Optional.of(card));

        cardService.cardBlocking(dto);

        assertEquals(Status.BLOCKED, card.getStatus());
    }

    @Test
    void cardActive_ShouldSetStatusToActive() {
        final var dto = CardActiveDto.builder().cardId(CARD_ID).build();
        final var card = new Card();
        
        when(cardRepository.findById(CARD_ID)).thenReturn(Optional.of(card));

        cardService.cardActive(dto);

        assertEquals(Status.ACTIVE, card.getStatus());
    }

    @Test
    void requestBlockCard_WhenUserIsOwner_ShouldBlockCard() {
        final var user = new User();

        user.setId(USER_ID);

        final var card = new Card();

        card.setUser(user);

        when(cardRepository.findById(CARD_ID)).thenReturn(Optional.of(card));

        cardService.requestBlockCard(CARD_ID, user);

        assertEquals(Status.BLOCKED, card.getStatus());
    }

    @Test
    void requestBlockCard_WhenUserIsNotOwner_ShouldThrowException() {
        final var otherUser = new User();

        otherUser.setId(2L);

        final var card = new Card();

        card.setUser(new User(USER_ID, null, null, null, null));

        when(cardRepository.findById(CARD_ID)).thenReturn(Optional.of(card));

        assertThrows(AccessDeniedException.class, () -> cardService.requestBlockCard(CARD_ID, otherUser));
    }

}
