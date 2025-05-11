package com.tyrdanov.bank_card_management_system.dto.request;

import java.time.LocalDate;

import com.tyrdanov.bank_card_management_system.enums.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Фильтр для поиска карт")
public class CardFilterRequest {
    
    @Schema(description = "Фильтр по ID пользователя", example = "101")
    Long userId;

    @Schema(description = "Фильтр по статусу карты", example = "ACTIVE")
    Status status;

    @Schema(description = "Фильтр по сроку действия", example = "2025-12-31")
    LocalDate validityPeriod;

    @Schema(description = "Поиск по полному номеру карты", example = "4111111111111111")
    String cardNumber;

    @Schema(description = "Фильтр по минимальному балансу", example = "100.0")
    Double balance;

}
