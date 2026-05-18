package com.example.leets7th.domain.auth.controller;

import com.example.leets7th.domain.auth.dto.OAuthResponseDto;
import com.example.leets7th.domain.auth.service.OAuthService;
import com.example.leets7th.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oAuthService;


    public ApiResponse<?> getKakaoOAuth(@RequestParam String code) throws Exception {
        //토큰 발급
        OAuthResponseDto.KakaoToken token = oAuthService.getKakaoToken(code);

        // 리소스 서버 접근
        oAuthService.getUserResource(token);

        //로그인  회원가입 분기처리

    }
}
