package com.example.leets7th.domain.user.dto;

public class UserRequestDto {
    public record UserCreateReq(String loginId,String name,String password,String email) {}
}
