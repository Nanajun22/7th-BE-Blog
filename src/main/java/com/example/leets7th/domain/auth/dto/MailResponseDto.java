package com.example.leets7th.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class MailResponseDto {
    public record Verify(@NotBlank String token) {}
}
