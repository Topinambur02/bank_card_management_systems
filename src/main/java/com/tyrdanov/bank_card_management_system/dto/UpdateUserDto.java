package com.tyrdanov.bank_card_management_system.dto;

import java.util.List;

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
@Schema(description = "DTO для обновления данных пользователя")
public class UpdateUserDto {
    
    @Schema(description = "ID пользователя", example = "101")
    Long id;

    @Schema(description = "Новый email", example = "updated@example.com")
    String email;

    @Schema(description = "Новый пароль", example = "updatedPassword456!")
    String password;

    @Schema(description = "Список ID привязанных карт", example = "[123, 456]")
    List<Long> cardIds;

    @Schema(description = "Новый ID роли", example = "1")
    Long roleId;

}
