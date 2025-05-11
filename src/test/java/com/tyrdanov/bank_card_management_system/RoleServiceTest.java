package com.tyrdanov.bank_card_management_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import com.tyrdanov.bank_card_management_system.dto.CreateRoleDto;
import com.tyrdanov.bank_card_management_system.dto.RoleDto;
import com.tyrdanov.bank_card_management_system.exception.ResourceNotFoundException;
import com.tyrdanov.bank_card_management_system.mapper.RoleMapper;
import com.tyrdanov.bank_card_management_system.model.Role;
import com.tyrdanov.bank_card_management_system.model.User;
import com.tyrdanov.bank_card_management_system.repository.RoleRepository;
import com.tyrdanov.bank_card_management_system.repository.UserRepository;
import com.tyrdanov.bank_card_management_system.service.RoleService;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    
    @Mock
    private RoleMapper mapper;

    @Mock
    private RoleRepository repository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void getAll_ShouldReturnListOfRoleDtos() {
        final var role = new Role();
        final var roleDto = new RoleDto();

        when(repository.findAll()).thenReturn(List.of(role));
        when(mapper.toDto(role)).thenReturn(roleDto);

        final var result = roleService.getAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
        verify(mapper).toDto(role);
    }

    @Test
    void getById_WhenRoleExists_ShouldReturnRoleDto() {
        final var id = 1L;
        final var role = new Role();
        final var roleDto = new RoleDto();

        when(repository.findById(id)).thenReturn(Optional.of(role));
        when(mapper.toDto(role)).thenReturn(roleDto);

        final var result = roleService.getById(id);

        assertNotNull(result);
        verify(repository).findById(id);
        verify(mapper).toDto(role);
    }

    @Test
    void getById_WhenRoleNotExists_ShouldThrowException() {
        final var id = 999L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleService.getById(id));
    }

    @Test
    void create_ShouldSaveAndReturnRoleDto() {
        final var createDto = new CreateRoleDto();
        final var role = new Role();
        final var savedRole = new Role();
        final var roleDto = new RoleDto();

        when(mapper.toModel(createDto)).thenReturn(role);
        when(repository.save(role)).thenReturn(savedRole);
        when(mapper.toDto(savedRole)).thenReturn(roleDto);

        final var result = roleService.create(createDto);

        assertNotNull(result);
        verify(mapper).toModel(createDto);
        verify(repository).save(role);
        verify(mapper).toDto(savedRole);
    }

    @Test
    @Transactional
    void update_ShouldUpdateAndReturnRoleDto() {
        final var roleId = 1L;
        final var userIds = List.of(1L, 2L);
        final var dto = new RoleDto();

        dto.setId(roleId);
        dto.setUserIds(userIds);

        final var user1 = new User();
        final var user2 = new User();
        final var role = new Role();

        when(repository.findById(roleId)).thenReturn(Optional.of(role));
        when(userRepository.findAllById(userIds)).thenReturn(List.of(user1, user2));
        when(mapper.toDto(role)).thenReturn(dto);

        final var result = roleService.update(dto);

        assertNotNull(result);
        verify(repository).findById(roleId);
        verify(userRepository).findAllById(userIds);
        verify(mapper).update(eq(dto), eq(List.of(user1, user2)), eq(role));
        verify(mapper).toDto(role);
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        final var id = 1L;

        roleService.delete(id);

        verify(repository).deleteById(id);
    }

    @Test
    void update_WhenUsersNotFound_ShouldLinkOnlyExistingUsers() {
        final var roleId = 1L;
        final var userIds = List.of(1L, 3L);
        final var dto = new RoleDto();

        dto.setId(roleId);
        dto.setUserIds(userIds);

        final var user1 = new User();
        final var role = new Role();

        when(repository.findById(roleId)).thenReturn(Optional.of(role));
        when(userRepository.findAllById(userIds)).thenReturn(List.of(user1));

        roleService.update(dto);

        verify(mapper).update(eq(dto), eq(List.of(user1)), eq(role));
    }

}
