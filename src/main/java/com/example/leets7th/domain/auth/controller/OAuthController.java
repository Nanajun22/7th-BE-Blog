package com.example.leets7th.domain.auth.controller;

import com.example.leets7th.domain.auth.dto.OAuthResponseDto;
import com.example.leets7th.domain.auth.service.OAuthService;
import com.example.leets7th.domain.user.dto.UserResponseDto;
import com.example.leets7th.global.code.SuccessCode;
import com.example.leets7th.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class OAuthController {
    private final OAuthService oAuthService;


    @GetMapping("/code/kakao")
    public ApiResponse<UserResponseDto.AccessToken> getKakaoOAuth(@RequestParam String code,
                                           HttpServletResponse response) {
        //토큰 발급
        OAuthResponseDto.KakaoToken token = oAuthService.getKakaoToken(code);

        // 리소스 서버 접근
        OAuthResponseDto.KakaoUserInfo userInfo = oAuthService.getUserResource(token);

        //로그인

        UserResponseDto.TokenResult tokens = oAuthService.loginKakaoOAuth(userInfo);

        setRefreshTokenCookie(response,tokens.refreshToken());

        return ApiResponse.success(SuccessCode.GENERAL_OK, new UserResponseDto.AccessToken(tokens.accessToken()));
    }


    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
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
