package com.tyrdanov.bank_card_management_system.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO для обновления данных пользователя")
public class UpdateUserDto {
    
    @NotNull(message = "ID пользователя не может быть пустым")
    @Positive(message = "ID пользователя должен быть положительным числом")
    @Schema(description = "ID пользователя", example = "101")
    Long id;

    @Email(message = "Некорректный формат email")
    @Size(max = 255, message = "Email не может быть длиннее 255 символов")
    @Schema(description = "Новый email", example = "updated@example.com")
    String email;

    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    @Schema(description = "Новый пароль", example = "updatedPassword456!")
    String password;

    @Schema(description = "Список ID привязанных карт", example = "[123, 456]")
    List<@Positive(message = "ID карты должен быть положительным числом") Long> cardIds;

    @Positive(message = "ID роли должен быть положительным числом")
    @Schema(description = "Новый ID роли", example = "1")
    Long roleId;

}
