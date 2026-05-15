package com.example.leets7th.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class MailResponseDto {
    public record Verify(@NotBlank(message = "인증번호를 입력해주세요") String token) {}
}
