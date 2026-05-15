package com.example.leets7th.domain.user.controller;

import com.example.leets7th.domain.user.dto.UserRequestDto;
import com.example.leets7th.domain.user.dto.UserResponseDto;
import com.example.leets7th.domain.user.service.UserService;
import com.example.leets7th.global.code.SuccessCode;
import com.example.leets7th.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;



    // HTTPS 필수
    @PostMapping
    public ApiResponse<UserResponseDto.UserCreateRes> createUser(
            @RequestBody UserRequestDto.UserCreateReq request
    ) {
        UserResponseDto.UserCreateRes response =userService.createUser(request);

        return ApiResponse.success(SuccessCode.GENERAL_CREATED,response);
    }



}
