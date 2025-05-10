package com.tyrdanov.bank_card_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO для блокировки карты")
public class CardBlockingDto {  
    @Schema(description = "Уникальный идентификатор карты", example = "456", requiredMode = Schema.RequiredMode.REQUIRED)
    Long cardId;
}
