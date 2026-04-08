package com.example.leets7th.domain.post.dto;

import com.example.leets7th.domain.post.domain.Post;

import java.time.LocalDateTime;

public class PostResponseDto {
    public record ReadPost(String title,
                           String content,
                           String nickname,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt) {

        public static ReadPost from (Post post) {
            return new ReadPost(post.getTitle(),
                    post.getContent(),
                    post.getUser().getName(),
                    post.getCreatedAt(),
                    post.getUpdatedAt());
        }

    }

    public record CreatePost(Long postId,
                             String title,
                             String content,
                             String nickname,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt) {

        public static CreatePost from(Post post) {
            return new CreatePost(post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getUser().getName(),
                    post.getCreatedAt(),
                    post.getUpdatedAt());
        }

    }

    public record UpdatePost(String title,
                             String content,
                             String nickname,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt) {
        public static UpdatePost from(Post post) {
            return new UpdatePost(post.getTitle(),
                    post.getContent(),
                    post.getUser().getName(),
                    post.getCreatedAt(),
                    post.getUpdatedAt());
        }

    }

    public record ReadPostList(Long postId,
                               String title,
                               String nickname,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt) {

        public static ReadPostList from(Post post) {
            return new ReadPostList(post.getId(),
                    post.getTitle(),
                    post.getUser().getName(),
                    post.getCreatedAt(),
                    post.getUpdatedAt());
        }
    }

}
