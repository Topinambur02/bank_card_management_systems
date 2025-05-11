package com.tyrdanov.bank_card_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Информация о пользователе")
public class UserDto {
    
    @Schema(description = "ID пользователя", example = "101")
    Long id;

    @Schema(description = "Email пользователя", example = "user@example.com")
    String email;

}
