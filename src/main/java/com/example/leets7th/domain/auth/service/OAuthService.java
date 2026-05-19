package com.example.leets7th.domain.auth.service;

import com.example.leets7th.domain.auth.domain.OAuthProvider;
import com.example.leets7th.domain.auth.domain.SocialAccount;
import com.example.leets7th.domain.auth.dto.OAuthResponseDto;
import com.example.leets7th.domain.auth.repository.AuthCacheRepository;
import com.example.leets7th.domain.auth.repository.SocialAccountRepository;
import com.example.leets7th.domain.user.domain.User;
import com.example.leets7th.domain.user.domain.UserRepository;
import com.example.leets7th.domain.user.domain.UserRole;
import com.example.leets7th.domain.user.dto.UserResponseDto;
import com.example.leets7th.domain.user.service.UserService;
import com.example.leets7th.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthService {
    @Value("${spring.oauth2.kakao.client-id}")
    private String KAKAO_ID;

    @Value("${spring.oauth2.kakao.redirect-uri}")
    private String redirectURI;

    @Value("${spring.oauth2.kakao.token-uri}")
    private String tokenURI;

    @Value("${spring.oauth2.kakao.client-secret}")
    private String secret;

    @Value("${spring.oauth2.kakao.user-info-uri}")
    private String resourceURI;

    private final RestTemplate restTemplate;
    private final SocialAccountRepository socialAccountRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthCacheRepository authCacheRepository;
    private final UserRepository userRepository;

    //카카오 엑세스 토큰 요청 메서드
    public OAuthResponseDto.KakaoToken getKakaoToken(String code) {


        //헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //바디 설정
        MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","authorization_code");
        body.add("redirect_uri",redirectURI);
        body.add("client_id",KAKAO_ID);
        body.add("code",code);
        body.add("client_secret",secret);

        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(body,headers);

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



    //OAuth 로그인
    @Transactional
    public UserResponseDto.TokenResult loginKakaoOAuth(OAuthResponseDto.KakaoUserInfo userInfo) {

        // 계정 정보 없을시 생성
        SocialAccount socialAccount = socialAccountRepository
                .findByProviderAndProviderId(OAuthProvider.KAKAO,userInfo.id())
                .orElseGet(() -> createSocialAccount(userInfo));


        return issueToken(socialAccount.getUser().getId());
    }

    //socialAccount 생성
    private SocialAccount createSocialAccount(OAuthResponseDto.KakaoUserInfo userInfo) {
        User user = userService.findUserByEmail(userInfo.kakaoAccount().email())
                .orElseGet(() -> createUser(
                        userInfo.kakaoAccount().profile().nickname(),
                        userInfo.kakaoAccount().email())
                );

        SocialAccount socialAccount = SocialAccount.create(user,OAuthProvider.KAKAO,userInfo.id());
        return socialAccountRepository.save(socialAccount);
    }

    // 유저 생성

    private User createUser(String name,String email) {

        User user = User.createOAuthUser(name,email);
        return userRepository.save(user);
    }


    private UserResponseDto.TokenResult issueToken(Long userId) {
        String accessToken = jwtUtil.generateAccessToken(userId, UserRole.ROLE_USER);
        String refreshToken = jwtUtil.generateRefreshToken();

        authCacheRepository.saveRefreshToken(refreshToken,userId);


        return new UserResponseDto.TokenResult(accessToken,refreshToken);
    }


}
