package com.tyrdanov.bank_card_management_system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
    info = @Info(title = "Система Управления Банковскими Картами", version = "1.0.0", description = "Данная система предоставляет backend-часть управления банковскими картами"), 
    security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
    name = "bearerAuth", 
    description = "JWT authentication", 
    scheme = "bearer", 
    type = SecuritySchemeType.HTTP, 
    bearerFormat = "JWT", 
    in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}
