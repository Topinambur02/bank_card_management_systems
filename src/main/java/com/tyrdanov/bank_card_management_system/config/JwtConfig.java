package com.tyrdanov.bank_card_management_system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Configuration
@ConfigurationProperties("jwt")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtConfig {
    
    String secret;

    int expirationMs;

}
