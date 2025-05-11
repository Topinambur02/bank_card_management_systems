package com.tyrdanov.bank_card_management_system.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyrdanov.bank_card_management_system.dto.UpdateUserDto;
import com.tyrdanov.bank_card_management_system.dto.UserDto;
import com.tyrdanov.bank_card_management_system.exception.ResourceNotFoundException;
import com.tyrdanov.bank_card_management_system.mapper.UserMapper;
import com.tyrdanov.bank_card_management_system.repository.CardRepository;
import com.tyrdanov.bank_card_management_system.repository.RoleRepository;
import com.tyrdanov.bank_card_management_system.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final CardRepository cardRepository;
    private final RoleRepository roleRepository;

    public List<UserDto> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public UserDto getById(Long id) {
        final var user = repository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return mapper.toDto(user);
    }

    @Transactional
    public UserDto update(UpdateUserDto dto) {
        final var id = dto.getId();
        final var cardIds = dto.getCardIds();
        final var password = dto.getPassword();
        final var roleId = dto.getRoleId();
        final var encodedPassword = encoder.encode(password);
        final var cards = cardRepository.findAllById(cardIds);
        final var role = roleRepository
                .findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        final var user = repository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        mapper.update(dto, role, cards, user);
        user.setPassword(encodedPassword);

        return mapper.toDto(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
