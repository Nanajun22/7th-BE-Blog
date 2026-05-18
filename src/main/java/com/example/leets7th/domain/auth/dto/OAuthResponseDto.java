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
}
