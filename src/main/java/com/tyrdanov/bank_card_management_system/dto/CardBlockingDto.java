package com.tyrdanov.bank_card_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO для блокировки карты")
public class CardBlockingDto {
    @NotNull(message = "ID карты не может быть пустым")
    @Positive(message = "ID карты должен быть положительным числом")
    @Schema(description = "Уникальный идентификатор карты", example = "456", requiredMode = Schema.RequiredMode.REQUIRED)
    Long cardId;
}
