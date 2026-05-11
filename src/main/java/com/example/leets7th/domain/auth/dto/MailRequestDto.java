package com.example.leets7th.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class MailRequestDto {

    public record send(@NotBlank(message = "이메일은 필수입니다.") String email) {}

    public record verify(@NotBlank(message = "이메일은 필수입니다." )
                         String email,
                         @NotBlank(message = "인증번호를 입력해주세요.")
                         String authCode

    ) {

    }
}
