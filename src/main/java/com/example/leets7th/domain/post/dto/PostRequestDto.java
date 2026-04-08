package com.example.leets7th.domain.post.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public class PostRequestDto {



    public record Write(
            @NotBlank(message = "제목을 입력해주세요.")
            @Max(value = 255,message = "제목은 최대 255자까지 가능합니다.")
            String title,

            @NotBlank(message = "내용을 입력해주세요.")
            String content
    ) {

    }
}
