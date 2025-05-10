package com.tyrdanov.bank_card_management_system.dto;

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
@Schema(description = "DTO для аутентификации")
public class SignInDto {
    
    @Schema(description = "Email пользователя", example = "user@example.com")
    String email;

    @Schema(description = "Пароль пользователя", example = "securePassword123!")
    String password;

}
