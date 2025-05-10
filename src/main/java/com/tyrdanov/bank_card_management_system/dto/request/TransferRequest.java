package com.tyrdanov.bank_card_management_system.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferRequest {
    
    Long sourceCardId;
    
    Long targetCardId;
    
    Double amount;

}
