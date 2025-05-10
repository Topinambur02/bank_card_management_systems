package com.tyrdanov.bank_card_management_system.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyrdanov.bank_card_management_system.dto.request.TransferRequest;
import com.tyrdanov.bank_card_management_system.model.User;
import com.tyrdanov.bank_card_management_system.service.TransferService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {
    
    private final TransferService service;

    @PostMapping
    public void transferBetweenUserCards(@RequestBody TransferRequest request, @AuthenticationPrincipal User user) {
        service.transferBetweenUserCards(request, user);
    }

}
