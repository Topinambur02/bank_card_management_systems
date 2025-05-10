package com.tyrdanov.bank_card_management_system.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyrdanov.bank_card_management_system.dto.request.TransferRequest;
import com.tyrdanov.bank_card_management_system.enums.Status;
import com.tyrdanov.bank_card_management_system.exception.ResourceNotFoundException;
import com.tyrdanov.bank_card_management_system.model.Card;
import com.tyrdanov.bank_card_management_system.model.User;
import com.tyrdanov.bank_card_management_system.repository.CardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final CardRepository cardRepository;

    @Transactional
    public void transferBetweenUserCards(TransferRequest request, User user) {
        final var sourceCardId = request.getSourceCardId();
        final var targetCardId = request.getTargetCardId();
        final var amount = request.getAmount();

        if (sourceCardId.equals(targetCardId)) {
            throw new IllegalArgumentException("Cannot transfer to the same card");
        }

        final var sourceCard = cardRepository
                .findById(sourceCardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
                
        final var targetCard = cardRepository
                .findById(targetCardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        validateCardOwnership(user, sourceCard, targetCard);
        validateCardsStatus(sourceCard, targetCard);
        validateBalance(sourceCard, amount);

        sourceCard.setBalance(sourceCard.getBalance() - amount);
        targetCard.setBalance(targetCard.getBalance() + amount);
    }

    private void validateCardOwnership(User user, Card... cards) {
        for (Card card : cards) {
            if (!card.getUser().getId().equals(user.getId())) {
                throw new AccessDeniedException("Card does not belong to the user");
            }
        }
    }

    private void validateCardsStatus(Card... cards) {
        for (Card card : cards) {
            if (card.getStatus() != Status.ACTIVE) {
                throw new IllegalStateException("Card " + card.getId() + " is not active");
            }
        }
    }

    private void validateBalance(Card sourceCard, Double amount) {
        if (sourceCard.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

}
