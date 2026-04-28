package com.example.leets7th.domain.user.dto;

import com.example.leets7th.domain.user.domain.User;

public class UserResponseDto {
    public record UserCreateRes(Long userId,String name) {

        public static UserCreateRes from(User user) {
            return new UserCreateRes(user.getId(), user.getName());
        }
    }
}
