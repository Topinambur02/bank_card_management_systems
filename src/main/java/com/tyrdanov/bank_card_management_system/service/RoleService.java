package com.tyrdanov.bank_card_management_system.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyrdanov.bank_card_management_system.dto.CreateRoleDto;
import com.tyrdanov.bank_card_management_system.dto.RoleDto;
import com.tyrdanov.bank_card_management_system.exception.ResourceNotFoundException;
import com.tyrdanov.bank_card_management_system.mapper.RoleMapper;
import com.tyrdanov.bank_card_management_system.repository.RoleRepository;
import com.tyrdanov.bank_card_management_system.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleMapper mapper;
    private final RoleRepository repository;
    private final UserRepository userRepository;

    public List<RoleDto> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public RoleDto getById(Long id) {
        final var role = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        return mapper.toDto(role);
    }

    public RoleDto create(CreateRoleDto dto) {
        final var role = mapper.toModel(dto);
        final var createdRole = repository.save(role);

        return mapper.toDto(createdRole);
    }

    @Transactional
    public RoleDto update(RoleDto dto) {
        final var id = dto.getId();
        final var userIds = dto.getUserIds();
        final var users = userRepository.findAllById(userIds);
        final var role = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        mapper.update(dto, users, role);

        return mapper.toDto(role);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
