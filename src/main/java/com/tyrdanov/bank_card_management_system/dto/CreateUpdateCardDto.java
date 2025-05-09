package com.tyrdanov.bank_card_management_system.dto;

import java.time.LocalDate;

import com.tyrdanov.bank_card_management_system.enums.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUpdateCardDto {

    Long id;
    
    String cardNumber;

    LocalDate validityPeriod;

    Status status;

    Double balance = 0.0;

}
