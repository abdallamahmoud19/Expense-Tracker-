package com.edafa.ExpenseTracker.mapper;

import com.edafa.ExpenseTracker.dto.response.UserResponseDto;
import com.edafa.ExpenseTracker.entities.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(
                        user.getRoles().stream()
                                .map(r -> r.getName())
                                .collect(Collectors.toSet())
                )
                .enabled(user.isEnabled())
                .build();
    }
}