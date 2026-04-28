package com.example.leets7th.domain.user.controller;

import com.example.leets7th.domain.user.dto.UserRequestDto;
import com.example.leets7th.domain.user.dto.UserResponseDto;
import com.example.leets7th.domain.user.service.UserService;
import com.example.leets7th.global.code.SuccessCode;
import com.example.leets7th.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;



    //테스트 API
    @PostMapping
    public ApiResponse<UserResponseDto.UserCreateRes> createUser(@RequestBody UserRequestDto.UserCreateReq request) {
        UserResponseDto.UserCreateRes response =userService.createUser(request);

        return ApiResponse.success(SuccessCode.GENERAL_CREATED,response);
    }
}
