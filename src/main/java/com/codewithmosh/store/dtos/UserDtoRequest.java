package com.codewithmosh.store.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDtoRequest(
        @NotBlank(message = "User name is required")
        String name,
        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 8,max = 25,message = "Password should have minimum 8 characters")
        String password
) {
}
