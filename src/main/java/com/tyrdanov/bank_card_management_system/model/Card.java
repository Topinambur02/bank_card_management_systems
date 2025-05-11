package com.tyrdanov.bank_card_management_system.model;

import java.time.LocalDate;

import com.tyrdanov.bank_card_management_system.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String cardNumber;

    LocalDate validityPeriod;

    @Enumerated(EnumType.STRING)
    Status status;

    @Builder.Default
    Double balance = 0.0;

    @JoinColumn(name = "user_id")
    @ManyToOne
    User user;

}
