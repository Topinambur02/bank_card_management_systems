package com.tyrdanov.bank_card_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
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

    @Null(message = "ID роли должен генерироваться автоматически")
    @Schema(description = "ID роли (генерируется автоматически, если не указан)", example = "1")
    Long id;

    @NotBlank(message = "Название роли не может быть пустым")
    @Schema(description = "Название роли", example = "ADMIN")
    String name;
    
}
