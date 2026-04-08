package com.example.leets7th.domain.post.controller;

import com.example.leets7th.domain.post.Service.PostService;
import com.example.leets7th.domain.post.dto.PostRequestDto;
import com.example.leets7th.domain.post.dto.PostResponseDto;
import com.example.leets7th.global.code.SuccessCode;
import com.example.leets7th.global.common.BaseCode;
import com.example.leets7th.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;


    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDto.ReadPost> getPost(@PathVariable Long postId) {
        return ApiResponse.success(SuccessCode.POST_READ_OK,postService.getPost(postId));
    }

    @GetMapping
    public ApiResponse<List<PostResponseDto.ReadPostList>> getPostList() {
        return ApiResponse.success(SuccessCode.POST_LIST_READ_OK,postService.getPostList());
    }

    @PostMapping
    public ApiResponse<PostResponseDto.CreatePost> createPost(PostRequestDto.Write request,Long userId) {
        return ApiResponse.success(SuccessCode.POST_CREATED,postService.createPost(request,userId));
    }

    @PatchMapping("/{postId}")
    public ApiResponse<PostResponseDto.UpdatePost> createPost(@PathVariable Long postId, PostRequestDto.Write request) {
        return ApiResponse.success(SuccessCode.POST_UPDATE_OK,postService.updatePost(request,postId));
    }


    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.success(SuccessCode.POST_DELETE_OK);
    }

}
