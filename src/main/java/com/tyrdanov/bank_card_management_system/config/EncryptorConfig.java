package com.tyrdanov.bank_card_management_system.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("jasypt.encryptor")
public class EncryptorConfig {
    
    private String password;

    @Bean
    PooledPBEStringEncryptor encryptor() {
        final var encryptor = new PooledPBEStringEncryptor();

        encryptor.setPoolSize(4);
        encryptor.setPassword(password);

        return encryptor;
    }

}
