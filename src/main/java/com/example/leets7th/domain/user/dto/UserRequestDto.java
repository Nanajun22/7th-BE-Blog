package com.example.leets7th.domain.user.dto;

import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

public class UserRequestDto {
    public record UserCreateReq(

            @NotBlank(message = "ID를 입력해주세요.")
            String loginId,

            @NotBlank(message = "이름을 입력해주세요.")
            String name,

            @NotBlank(message = "비밀번호를 입력해주세요.")
            String password,

            @NotBlank(message = "이메일을 입력해주세요.")
            @email
            String email,

            @NotBlank(message = "이메일 인증을 해주세요.")
            String verifiedToken) {}
}
