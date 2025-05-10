package com.tyrdanov.bank_card_management_system.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {

    private String url;

    private String date;

    private String username;

    private String message;
    
}
