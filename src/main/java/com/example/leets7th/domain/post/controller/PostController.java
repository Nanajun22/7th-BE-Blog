package com.example.leets7th.domain.post.controller;

import com.example.leets7th.domain.post.Service.PostService;
import com.example.leets7th.domain.post.dto.PostRequestDto;
import com.example.leets7th.domain.post.dto.PostResponseDto;
import com.example.leets7th.global.code.SuccessCode;
import com.example.leets7th.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<PostResponseDto.CreatePost> createPost(@RequestBody @Valid PostRequestDto.Create request,
                                                              @RequestParam  Long userId
    ) {
        return ApiResponse.success(SuccessCode.POST_CREATED,postService.createPost(request,userId));
    }

    @PatchMapping("/{postId}")
    public ApiResponse<PostResponseDto.UpdatePost> createPost(@PathVariable Long postId,
                                                              @RequestBody @Valid PostRequestDto.Update request,
                                                              @RequestParam Long userId
    ) {
        return ApiResponse.success(SuccessCode.POST_UPDATE_OK,postService.updatePost(request,postId,userId));
    }


    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable Long postId,
                                        @RequestParam Long userId) {
        postService.deletePost(postId,userId);
        return ApiResponse.success(SuccessCode.POST_DELETE_OK);
    }

}
