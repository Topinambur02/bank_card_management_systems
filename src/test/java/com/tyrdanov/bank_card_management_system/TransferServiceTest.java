package com.tyrdanov.bank_card_management_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import com.tyrdanov.bank_card_management_system.dto.request.TransferRequest;
import com.tyrdanov.bank_card_management_system.enums.Status;
import com.tyrdanov.bank_card_management_system.exception.ResourceNotFoundException;
import com.tyrdanov.bank_card_management_system.model.Card;
import com.tyrdanov.bank_card_management_system.model.User;
import com.tyrdanov.bank_card_management_system.repository.CardRepository;
import com.tyrdanov.bank_card_management_system.service.TransferService;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {
    
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private TransferService transferService;

    @Test
    void transferBetweenUserCards_Success() {
        final var user = User.builder().id(1L).build();
        final var sourceCard = Card.builder().id(1L).balance(100.0).user(user).status(Status.ACTIVE).build();
        final var targetCard = Card.builder().id(2L).balance(50.0).user(user).status(Status.ACTIVE).build();
        final var request = TransferRequest.builder().sourceCardId(1L).targetCardId(2L).amount(30.0).build();

        when(cardRepository.findById(1L)).thenReturn(Optional.of(sourceCard));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(targetCard));

        transferService.transferBetweenUserCards(request, user);

        assertEquals(70.0, sourceCard.getBalance());
        assertEquals(80.0, targetCard.getBalance());
    }

    @Test
    void transferBetweenSameCard_ThrowsIllegalArgumentException() {
        final var user = User.builder().id(1L).build();
        final var request = TransferRequest.builder().sourceCardId(1L).targetCardId(1L).amount(50.0).build();
        final var exception = assertThrows(IllegalArgumentException.class, () -> transferService.transferBetweenUserCards(request, user));

        assertEquals("Cannot transfer to the same card", exception.getMessage());
    }

    @Test
    void sourceCardNotFound_ThrowsResourceNotFoundException() {
        final var user = User.builder().id(1L).build();
        final var request = TransferRequest.builder().sourceCardId(1L).targetCardId(2L).amount(50.0).build();

        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        final var exception = assertThrows(ResourceNotFoundException.class, () -> transferService.transferBetweenUserCards(request, user));

        assertEquals("Card not found", exception.getMessage());
    }

    @Test
    void targetCardNotFound_ThrowsResourceNotFoundException() {
        final var user = User.builder().id(1L).build();
        final var sourceCard = Card.builder().id(1L).balance(100.0).user(user).status(Status.ACTIVE).build();
        final var request = TransferRequest.builder().sourceCardId(1L).targetCardId(2L).amount(50.0).build();

        when(cardRepository.findById(1L)).thenReturn(Optional.of(sourceCard));
        when(cardRepository.findById(2L)).thenReturn(Optional.empty());

        final var exception = assertThrows(ResourceNotFoundException.class, () -> transferService.transferBetweenUserCards(request, user));

        assertEquals("Card not found", exception.getMessage());
    }

    @Test
    void cardNotOwnedByUser_ThrowsAccessDeniedException() {
        final var user1 = User.builder().id(1L).build();
        final var user2 = User.builder().id(2L).build();
        final var sourceCard = Card.builder().id(1L).balance(100.0).user(user1).status(Status.ACTIVE).build();
        final var targetCard = Card.builder().id(2L).balance(50.0).user(user2).status(Status.ACTIVE).build();
        final var request = TransferRequest.builder().sourceCardId(1L).targetCardId(2L).amount(30.0).build();

        when(cardRepository.findById(1L)).thenReturn(Optional.of(sourceCard));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(targetCard));

        final var exception = assertThrows(AccessDeniedException.class, () -> transferService.transferBetweenUserCards(request, user1));

        assertEquals("Card does not belong to the user", exception.getMessage());
    }

    @Test
    void inactiveSourceCard_ThrowsIllegalStateException() {
        final var user = User.builder().id(1L).build();
        final var sourceCard = Card.builder().id(1L).balance(100.0).user(user).status(Status.BLOCKED).build();
        final var targetCard = Card.builder().id(2L).balance(50.0).user(user).status(Status.ACTIVE).build();
        final var request = TransferRequest.builder().sourceCardId(1L).targetCardId(2L).amount(30.0).build();

        when(cardRepository.findById(1L)).thenReturn(Optional.of(sourceCard));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(targetCard));

        final var exception = assertThrows(IllegalStateException.class, () -> transferService.transferBetweenUserCards(request, user));

        assertEquals("Card 1 is not active", exception.getMessage());
    }

    @Test
    void insufficientBalance_ThrowsIllegalArgumentException() {
        final var user = User.builder().id(1L).build();
        final var sourceCard = Card.builder().id(1L).balance(20.0).user(user).status(Status.ACTIVE).build();
        final var targetCard = Card.builder().id(2L).balance(50.0).user(user).status(Status.ACTIVE).build();
        final var request = TransferRequest.builder().sourceCardId(1L).targetCardId(2L).amount(30.0).build();

        when(cardRepository.findById(1L)).thenReturn(Optional.of(sourceCard));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(targetCard));

        final var exception = assertThrows(IllegalArgumentException.class, () -> transferService.transferBetweenUserCards(request, user));

        assertEquals("Insufficient funds", exception.getMessage());
    }

}
