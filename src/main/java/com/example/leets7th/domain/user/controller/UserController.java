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


    @PostMapping("/login")
    public ApiResponse<UserResponseDto.AccessToken> loginUser(@RequestBody UserRequestDto.Login request,
                                                        HttpServletResponse response) {
        UserResponseDto.TokenResult tokens = userService.loginUser(request);
        setRefreshTokenCookie(response, tokens.refreshToken());
        return ApiResponse.success(SuccessCode.GENERAL_OK,new UserResponseDto.AccessToken(tokens.accessToken()));
    }

    @PostMapping("/reissue")
    public ApiResponse<UserResponseDto.AccessToken> reissueToken(
            @CookieValue (name = "refreshToken") String refreshToken,
            HttpServletResponse response) {

        UserResponseDto.TokenResult tokens = userService.reissueToken(refreshToken);

        setRefreshTokenCookie(response,tokens.refreshToken());

        return ApiResponse.success(SuccessCode.GENERAL_OK, new UserResponseDto.AccessToken(tokens.accessToken()));

    }


    private void setRefreshTokenCookie(HttpServletResponse response,String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken",refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/api/users")
                .maxAge(60*60*24*7)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE,cookie.toString());
    }
}
