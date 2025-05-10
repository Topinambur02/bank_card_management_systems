package com.tyrdanov.bank_card_management_system.dto;

import java.time.LocalDate;

import com.tyrdanov.bank_card_management_system.enums.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Информация о карте")
public class CardDto {

    @Schema(description = "ID карты", example = "789")
    Long id;
    
    @Schema(description = "Маскированный номер карты", example = "1234 **** **** 5678")
    String maskedCardNumber;

    @Schema(description = "Срок действия карты", example = "2025-12-31")
    LocalDate validityPeriod;

    @Schema(description = "Статус карты (например: ACTIVE, BLOCKED)", example = "ACTIVE")
    Status status;

    @Schema(description = "Текущий баланс на карте", example = "1500.0")
    Double balance = 0.0;

    @Schema(description = "ID владельца карты", example = "101")
    Long userId;

}
