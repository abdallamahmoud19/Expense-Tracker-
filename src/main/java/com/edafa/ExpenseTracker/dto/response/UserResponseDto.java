package com.edafa.ExpenseTracker.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class UserResponseDto {
    Long id;
    String firstName;
    String lastName;
    String email;
    Set<String> roles;
    boolean enabled;
}