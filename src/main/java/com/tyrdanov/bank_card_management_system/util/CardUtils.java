package com.tyrdanov.bank_card_management_system.util;

public class CardUtils {
    public static String maskCardNumber(String cardNumber) {
        final var cleaned = cardNumber.replaceAll("[^0-9]", "");

        if (cleaned.length() < 12) return "****";

        final var lastFour = cleaned.substring(cleaned.length() - 4);
        
        return "**** **** **** " + lastFour;
    }
}
