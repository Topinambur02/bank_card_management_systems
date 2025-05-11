package com.tyrdanov.bank_card_management_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tyrdanov.bank_card_management_system.dto.UpdateUserDto;
import com.tyrdanov.bank_card_management_system.dto.UserDto;
import com.tyrdanov.bank_card_management_system.exception.ResourceNotFoundException;
import com.tyrdanov.bank_card_management_system.mapper.UserMapper;
import com.tyrdanov.bank_card_management_system.model.Card;
import com.tyrdanov.bank_card_management_system.model.Role;
import com.tyrdanov.bank_card_management_system.model.User;
import com.tyrdanov.bank_card_management_system.repository.CardRepository;
import com.tyrdanov.bank_card_management_system.repository.RoleRepository;
import com.tyrdanov.bank_card_management_system.repository.UserRepository;
import com.tyrdanov.bank_card_management_system.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getAll_ShouldReturnListOfUsers() {
        final var user = User.builder().build();
        final var userDto = UserDto.builder().build();

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        final var result = userService.getAll();

        assertEquals(1, result.size());
        verify(userRepository).findAll();
        verify(userMapper).toDto(user);
    }

    @Test
    void getById_WhenUserExists_ShouldReturnUserDto() {
        final var userId = 1L;
        final var user = User.builder().build();
        final var userDto = UserDto.builder().build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        final var result = userService.getById(userId);

        assertNotNull(result);
        verify(userRepository).findById(userId);
    }

    @Test
    void getById_WhenUserNotExists_ShouldThrowException() {
        final var userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.getById(userId));
    }

    @Test
    void update_WhenValidData_ShouldReturnUpdatedUserDto() {
        final var userId = 1L;
        final var roleId = 2L;
        final var cardIds = List.of(3L, 4L);
        final var rawPassword = "password";
        final var encodedPassword = "encodedPassword";
        final var dto = UpdateUserDto
                .builder()
                .id(userId)
                .password(rawPassword)
                .roleId(roleId)
                .cardIds(cardIds)
                .build();
        final var role = Role.builder().build();
        final var cards = List.of(Card.builder().build(), Card.builder().build());
        final var user = User.builder().build();
        final var userDto = UserDto.builder().build();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(cardRepository.findAllById(cardIds)).thenReturn(cards);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userMapper.toDto(user)).thenReturn(userDto);

        final var result = userService.update(dto);

        assertNotNull(result);
        verify(roleRepository).findById(roleId);
        verify(cardRepository).findAllById(cardIds);
        verify(userRepository).findById(userId);
        verify(bCryptPasswordEncoder).encode(rawPassword);
        verify(userMapper).update(dto, role, cards, user);
        assertEquals(encodedPassword, user.getPassword());
    }

    @Test
    void update_WhenRoleNotFound_ShouldThrowException() {
        final var roleId = 999L;
        final var dto = UpdateUserDto.builder().roleId(roleId).build();

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.update(dto));
    }

    @Test
    void delete_ShouldInvokeRepositoryDelete() {
        final var userId = 1L;

        userService.delete(userId);

        verify(userRepository).deleteById(userId);
    }

}
