package com.tyrdanov.bank_card_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO для активации карты")
public class CardActiveDto {
    @Schema(description = "Уникальный идентификатор карты", example = "123")
    Long cardId;
}
