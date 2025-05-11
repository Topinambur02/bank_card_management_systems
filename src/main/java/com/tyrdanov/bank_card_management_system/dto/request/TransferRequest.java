package com.tyrdanov.bank_card_management_system.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Запрос на перевод средств между картами")
public class TransferRequest {
    
    @Schema(description = "ID исходной карты", example = "123")
    Long sourceCardId;
    
    @Schema(description = "ID целевой карты", example = "456")
    Long targetCardId;
    
    @Schema(description = "Сумма перевода (положительное значение)", example = "500.0")
    Double amount;

}
