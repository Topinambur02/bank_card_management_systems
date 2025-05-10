package com.tyrdanov.bank_card_management_system.specification;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.tyrdanov.bank_card_management_system.dto.request.CardFilterRequest;
import com.tyrdanov.bank_card_management_system.model.Card;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

@Component
public class CardSpecification {

    public Specification<Card> filter(CardFilterRequest request) {
        return (root, query, builder) -> {
            final var predicates = List.of(
                    likePredicate(builder, root.get("cardNumber"), request.getCardNumber()),
                    equalPredicate(builder, root.get("user").get("id"), request.getUserId()),
                    equalPredicate(builder, root.get("balance"), request.getBalance()),
                    equalPredicate(builder, root.get("status"), request.getStatus()),
                    equalPredicate(builder, root.get("validityPeriod"), request.getValidityPeriod()));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Predicate likePredicate(CriteriaBuilder builder, Expression<String> expression, String value) {
        return Optional
                .ofNullable(value)
                .map(val -> builder.like(expression, val))
                .orElseGet(builder::conjunction);
    }

    private Predicate equalPredicate(CriteriaBuilder builder, Expression<?> expression, Object value) {
        return Optional
                .ofNullable(value)
                .map(val -> builder.equal(expression, val))
                .orElseGet(builder::conjunction);
    }

}
