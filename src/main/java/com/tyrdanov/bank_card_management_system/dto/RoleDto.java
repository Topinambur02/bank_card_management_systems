package com.tyrdanov.bank_card_management_system.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Информация о роли")
public class RoleDto {
    @NotNull(message = "ID ролли не может быть пустым")
    @Positive(message = "ID роли должен быть положительным числом")
    @Schema(description = "ID роли", example = "2")
    Long id;

    @NotBlank(message = "Название роли не может быть пустым")
    @Schema(description = "Название роли", example = "USER")
    String name;

    @Schema(description = "Список ID пользователей с этой ролью", example = "[101, 102]")
    List<@Positive(message = "ID пользователя должен быть положительным числом") Long> userIds;

}
