package com.tyrdanov.bank_card_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tyrdanov.bank_card_management_system.dto.CardDto;
import com.tyrdanov.bank_card_management_system.model.Card;

@Mapper
public interface CardMapper {
    
    Card toModel(CardDto dto);

    CardDto toDto(Card card);

    void update(CardDto dto, @MappingTarget Card card);
    
}
