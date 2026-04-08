package com.example.leets7th.domain.post.dto;

import java.time.LocalDateTime;

public class PostResponseDto {
    public record ReadPost(String title,
                           String content,
                           String nickname,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt) {

    }

    public record CreatePost(Long postId,
                             String title,
                             String content,
                             String nickname,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt) {

    }

    public record UpdatePost(String title,
                             String content,
                             String nickname,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt) {

    }

    public record ReadPostList(Long postId,
                               String title,
                               String nickname,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt) {

    }

}
