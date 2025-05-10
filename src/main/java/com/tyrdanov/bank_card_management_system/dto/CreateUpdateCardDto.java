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
@Schema(description = "DTO для создания/обновления карты")
public class CreateUpdateCardDto {

    @Schema(description = "ID карты (автогенерация при создании)", example = "202")
    Long id;
    
    @Schema(description = "Полный номер карты", example = "1234567812345678")
    String cardNumber;

    @Schema(description = "Срок действия карты", example = "2026-05-31")
    LocalDate validityPeriod;

    @Schema(description = "Статус карты", example = "ACTIVE")
    Status status;

    @Schema(description = "Баланс", example = "0.0")
    Double balance = 0.0;

    @Schema(description = "ID владельца карты", example = "102")
    Long userId;

}
