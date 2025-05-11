package com.tyrdanov.bank_card_management_system.dto;

import java.time.LocalDate;

import com.tyrdanov.bank_card_management_system.enums.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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

    @Positive(message = "ID карты должен быть положительным числом")
    @Schema(description = "ID карты (автогенерация при создании)", example = "202")
    Long id;
    
    @NotBlank(message = "Номер карты не может быть пустым")
    @Pattern(regexp = "^\\d{16}$", message = "Номер карты должен состоять из 16 цифр")
    @Schema(description = "Полный номер карты", example = "1234567812345678")
    String cardNumber;

    @NotNull(message = "Срок действия не может быть пустым")
    @FutureOrPresent(message = "Срок действия карты должен быть в будущем или настоящем")
    @Schema(description = "Срок действия карты", example = "2026-05-31")
    LocalDate validityPeriod;

    @NotNull(message = "Статус карты не может быть пустым")
    @Schema(description = "Статус карты", example = "ACTIVE")
    Status status;

    @DecimalMin(value = "0.0", message = "Баланс не может быть отрицательным")
    @Schema(description = "Баланс", example = "0.0")
    Double balance = 0.0;

    @NotNull(message = "ID пользователя не может быть пустым")
    @Positive(message = "ID пользователя должен быть положительным числом")
    @Schema(description = "ID владельца карты", example = "102")
    Long userId;

}
