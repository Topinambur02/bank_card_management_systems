package com.tyrdanov.bank_card_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tyrdanov.bank_card_management_system.controller.advice.GlobalExceptionControllerAdvice;
import com.tyrdanov.bank_card_management_system.filter.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final GlobalExceptionControllerAdvice exceptionHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        requests -> requests
                                .requestMatchers(HttpMethod.POST, "/cards", "/cards/block", "/cards/active").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/cards/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/cards").hasRole("ADMIN")
                                .requestMatchers("/users/**").hasRole("ADMIN")
                                .requestMatchers("/cards/filter**").hasRole("USER")
                                .requestMatchers("/{cardId}/request-block").hasRole("USER")
                                .requestMatchers("/transfer").hasRole("USER")
                                .anyRequest().permitAll())
                .anonymous(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(exceptionHandler))
                .formLogin(login -> login.loginProcessingUrl("/login"))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        final var provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());

        return new ProviderManager(provider);
    }

}
