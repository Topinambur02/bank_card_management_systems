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
@Schema(description = "DTO для регистрации пользователя")
public class SignUpDto {
    
    @Schema(description = "Email пользователя", example = "newuser@example.com")
    String email;

    @Schema(description = "Пароль пользователя", example = "newPassword123!")
    String password;

    @Schema(description = "ID роли", example = "2")
    Long roleId;

}
