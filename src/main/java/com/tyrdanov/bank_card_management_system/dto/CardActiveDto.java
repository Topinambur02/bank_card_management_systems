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
@Schema(description = "DTO для активации карты")
public class CardActiveDto {
    @NotNull(message = "ID карты не может быть пустым")
    @Positive(message = "ID карты должен быть положительным числом")
    @Schema(description = "Уникальный идентификатор карты", example = "123")
    Long cardId;
}
