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
@Schema(description = "DTO для создания роли")
public class CreateRoleDto {

    @Schema(description = "ID роли (генерируется автоматически, если не указан)", example = "1")
    Long id;

    @Schema(description = "Название роли", example = "ADMIN")
    String name;
    
}
