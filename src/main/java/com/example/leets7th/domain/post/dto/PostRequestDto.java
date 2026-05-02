package com.example.leets7th.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public class PostRequestDto {




    public record PostCreateReq(
            @NotBlank(message = "제목을 입력해주세요.")
            @Size(max = 255,message = "제목은 최대 255자까지 가능합니다.")
            String title,

            @NotBlank(message = "내용을 입력해주세요.")
            String content
    ) {
    }


    public record PostUpdateReq(

            @NotBlank(message = "제목을 입력해주세요.")
            @Size(max = 255, message = "제목은 최대 255자까지 가능합니다.")
            String title,


            @NotBlank(message = "내용을 입력해주세요.")
            String content
    ) {
    }
}
