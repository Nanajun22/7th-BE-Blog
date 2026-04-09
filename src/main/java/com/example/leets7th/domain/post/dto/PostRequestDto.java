package com.example.leets7th.domain.post.dto;

import com.example.leets7th.domain.post.domain.Post;
import com.example.leets7th.domain.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class PostRequestDto {




    public record Create(
            @NotBlank(message = "제목을 입력해주세요.")
            @Size(max = 255,message = "제목은 최대 255자까지 가능합니다.")
            String title,

            @NotBlank(message = "내용을 입력해주세요.")
            String content
    ) {
        public Post toEntity(User user) {
            return Post.builder()
                    .title(this.title())
                    .content(this.content)
                    .user(user)
                    .build();
        }
    }


    public record Update(
            @NotBlank(message = "제목을 입력해주세요.")
            @Size(max = 255, message = "제목은 최대 255자까지 가능합니다.")
            String title,

            @NotBlank(message = "내용을 입력해주세요.")
            String content
    ) {
    }
}
