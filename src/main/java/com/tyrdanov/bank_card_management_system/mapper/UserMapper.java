package com.tyrdanov.bank_card_management_system.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tyrdanov.bank_card_management_system.dto.UpdateUserDto;
import com.tyrdanov.bank_card_management_system.dto.UserDto;
import com.tyrdanov.bank_card_management_system.model.Card;
import com.tyrdanov.bank_card_management_system.model.Role;
import com.tyrdanov.bank_card_management_system.model.User;

@Mapper
public interface UserMapper {
    
    UserDto toDto(User user);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "authorities", ignore = true)
    void update(UpdateUserDto dto, Role role, List<Card> cards, @MappingTarget User user);
    
}
