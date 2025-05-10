package com.tyrdanov.bank_card_management_system.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardActiveDto {
    Long cardId;
}
