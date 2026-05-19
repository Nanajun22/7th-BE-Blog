package com.example.leets7th.domain.auth.controller;

import com.example.leets7th.domain.auth.service.AuthService;
import com.example.leets7th.domain.user.dto.UserRequestDto;
import com.example.leets7th.domain.user.dto.UserResponseDto;
import com.example.leets7th.global.code.SuccessCode;
import com.example.leets7th.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ApiResponse<UserResponseDto.AccessToken> loginUser(@RequestBody UserRequestDto.Login request,
                                                              HttpServletResponse response) {
        UserResponseDto.TokenResult tokens = authService.loginUser(request);
        setRefreshTokenCookie(response, tokens.refreshToken());
        return ApiResponse.success(SuccessCode.GENERAL_OK,new UserResponseDto.AccessToken(tokens.accessToken()));
    }


    @PostMapping("/reissue")
    public ApiResponse<UserResponseDto.AccessToken> reissueToken(@CookieValue(name = "refreshToken") String refreshToken,
                                                                 HttpServletResponse response) {

        UserResponseDto.TokenResult tokens = authService.reissueToken(refreshToken);

        setRefreshTokenCookie(response,tokens.refreshToken());

        return ApiResponse.success(SuccessCode.GENERAL_OK, new UserResponseDto.AccessToken(tokens.accessToken()));

    }



    private void setRefreshTokenCookie(HttpServletResponse response,String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken",refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/api/auth/reissue")
                .maxAge(60*60*24*7)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE,cookie.toString());
    }

}
