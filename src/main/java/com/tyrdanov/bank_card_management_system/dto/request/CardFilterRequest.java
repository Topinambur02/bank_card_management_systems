package com.tyrdanov.bank_card_management_system.dto.request;

import java.time.LocalDate;

import com.tyrdanov.bank_card_management_system.enums.Status;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardFilterRequest {
    
    Long userId;

    Status status;

    LocalDate validityPeriod;

    String cardNumber;

    Double balance;

}
