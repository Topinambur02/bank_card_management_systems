package com.tyrdanov.bank_card_management_system.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.tyrdanov.bank_card_management_system.dto.CreateRoleDto;
import com.tyrdanov.bank_card_management_system.dto.RoleDto;
import com.tyrdanov.bank_card_management_system.model.Role;
import com.tyrdanov.bank_card_management_system.model.User;

@Mapper
public interface RoleMapper {

    @Mapping(target = "userIds", source = ".", qualifiedByName = "getUserIds")
    RoleDto toDto(Role role);

    @Mapping(target = "users", ignore = true)
    Role toModel(CreateRoleDto dto);

    void update(RoleDto dto, List<User> users, @MappingTarget Role role);

    @Named("getUserIds")
    default List<Long> getUserIds(Role role) {
        return role
                .getUsers()
                .stream()
                .map(User::getId)
                .toList();
    }

}
