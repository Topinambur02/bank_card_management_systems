package com.tyrdanov.bank_card_management_system.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyrdanov.bank_card_management_system.dto.request.TransferRequest;
import com.tyrdanov.bank_card_management_system.model.User;
import com.tyrdanov.bank_card_management_system.service.TransferService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
@Tag(name = "Денежные переводы", description = "API для операций перевода средств между картами")
public class TransferController {
    
    private final TransferService service;

    @Operation(
        summary = "Перевод между картами пользователей",
        description = "Выполняет перевод денежных средств между картами текущего авторизованного пользователя"
    )
    @PostMapping
    public void transferBetweenUserCards(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Данные для перевода",
            required = true,
            content = @Content(schema = @Schema(implementation = TransferRequest.class))
        )    
        @Valid @RequestBody TransferRequest request, 
        @Parameter(hidden = true) @AuthenticationPrincipal User user
    ) {
        service.transferBetweenUserCards(request, user);
    }

}
