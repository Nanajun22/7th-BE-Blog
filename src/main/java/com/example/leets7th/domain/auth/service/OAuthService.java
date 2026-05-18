package com.example.leets7th.domain.auth.service;

import com.example.leets7th.domain.auth.dto.OAuthResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class OAuthService {
    @Value("${KAKAO_ID}")
    private String KAKAO_ID;

    @Value("${redirect-uri}")
    private String redirectURI;

    @Value("${token-uri}")
    private String tokenURI;

    @Value("${KAKAO_SECRET}")
    private String secret;

    @Value("${user-info-uri}")
    private String resourceURI;

    private final RestTemplate restTemplate;

    //카카오 엑세스 토큰 요청 메서드
    public OAuthResponseDto.KakaoToken getKakaoToken(String code) {


        //헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //바디 설정
        MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","authorization_code");
        body.add("redirect-uri",redirectURI);
        body.add("client_id",KAKAO_ID);
        body.add("code",code);
        body.add("client_secret",secret);

        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(headers,body);

        // 토큰 요청
        ResponseEntity<OAuthResponseDto.KakaoToken> response = restTemplate.exchange(
                tokenURI,
                HttpMethod.POST,
                httpEntity,
                OAuthResponseDto.KakaoToken.class
        );


        return response.getBody();

    }


    //유저 리소스 요청
    public OAuthResponseDto.KakaoUserInfo getUserResource(OAuthResponseDto.KakaoToken token) {


        // 헤더 토큰 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization","Bearer "+token.accessToken());


        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);


        ResponseEntity<OAuthResponseDto.KakaoUserInfo> response = restTemplate.exchange(
                resourceURI,
                HttpMethod.GET,
                httpEntity,
                OAuthResponseDto.KakaoUserInfo.class
        );




        return response.getBody();
    }


}
