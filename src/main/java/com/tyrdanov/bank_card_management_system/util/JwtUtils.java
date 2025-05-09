package com.tyrdanov.bank_card_management_system.util;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.tyrdanov.bank_card_management_system.config.JwtConfig;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtConfig config;

    public String generateToken(UserDetails userDetails) {
        final var username = userDetails.getUsername();
        final var expirationMs = config.getExpirationMs();
        final var expirationDate = new Date(System.currentTimeMillis() + expirationMs);
        final var issuedAtDate = new Date();

        return Jwts
                .builder()
                .subject(username)
                .issuedAt(issuedAtDate)
                .expiration(expirationDate)
                .signWith(key())
                .compact();
    }

    public String getUsername(String token) {
        return Jwts
                .parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .verifyWith(key())
                    .build()
                    .parse(token);

            return true;
        }

        catch (Exception e) {
            return false;
        }
    }

    private SecretKey key() {
        final var secret = config.getSecret();

        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

}
