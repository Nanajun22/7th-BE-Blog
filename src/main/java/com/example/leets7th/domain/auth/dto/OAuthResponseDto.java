package com.example.leets7th.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthResponseDto {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record KakaoToken(
            @JsonProperty("access_token")
            String accessToken
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record KakaoUserInfo(
            @JsonProperty("id")
            Long id,
            @JsonProperty("kakao_account")
            KakaoAccount kakaoAccount) {

        public record KakaoAccount(
                @JsonProperty("email")
                String email,
                @JsonProperty("profile")
                Profile profile
        ) {
            public record Profile(@JsonProperty("nickname") String nickname) {

            }

        }

    }
}
