package com.example.leets7th.domain.post.dto;

public class PostRequestDto {

    public record Read() {}

    public record Write(String title,String content) {}
}
