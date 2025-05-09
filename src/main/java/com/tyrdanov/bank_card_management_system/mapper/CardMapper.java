package com.tyrdanov.bank_card_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.tyrdanov.bank_card_management_system.dto.CardDto;
import com.tyrdanov.bank_card_management_system.dto.CreateUpdateCardDto;
import com.tyrdanov.bank_card_management_system.model.Card;
import com.tyrdanov.bank_card_management_system.util.CardUtils;

@Mapper
public interface CardMapper {
    
    Card toModel(CreateUpdateCardDto dto);

    @Mapping(target = "maskedCardNumber", source = ".", qualifiedByName = "getMaskedCardNumber")
    CardDto toDto(Card card);

    void update(CreateUpdateCardDto dto, @MappingTarget Card card);

    @Named("getMaskedCardNumber")
    default String getMaskedCardNumber(Card card) {
        final var cardNumber = card.getCardNumber();
        return CardUtils.maskCardNumber(cardNumber);
    }
    
}
