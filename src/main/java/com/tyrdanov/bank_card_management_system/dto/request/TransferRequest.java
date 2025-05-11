package com.tyrdanov.bank_card_management_system.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Запрос на перевод средств между картами")
public class TransferRequest {
    
    @NotNull(message = "ID исходной карты не может быть пустым")
    @Positive(message = "ID исходной карты должен быть положительным числом")
    @Schema(description = "ID исходной карты", example = "123")
    Long sourceCardId;
    
    @NotNull(message = "ID целевой карты не может быть пустым")
    @Positive(message = "ID целевой карты должен быть положительным числом")
    @Schema(description = "ID целевой карты", example = "456")
    Long targetCardId;
    
    @NotNull(message = "Сумма перевода не может быть пустой")
    @DecimalMin(value = "0.01", message = "Сумма перевода должна быть не менее 0.01")
    @Schema(description = "Сумма перевода (положительное значение)", example = "500.0")
    Double amount;

}
