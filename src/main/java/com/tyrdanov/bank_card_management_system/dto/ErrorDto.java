package com.tyrdanov.bank_card_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Информация об ошибке")
public class ErrorDto {

    @Schema(description = "URL запроса", example = "/api/cards")
    private String url;

    @Schema(description = "Время возникновения ошибки", example = "2023-08-15T12:34:56")
    private String date;

    @Schema(description = "Имя пользователя", example = "user@example.com")
    private String username;

    @Schema(description = "Сообщение об ошибке", example = "Карта не найдена")
    private String message;
    
}
