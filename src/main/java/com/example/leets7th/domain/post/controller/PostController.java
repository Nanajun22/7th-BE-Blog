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
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController implements PostControllerDocs {
    private final PostService postService;


    @Override
    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDto.ReadPost> getPost(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        return ApiResponse.success(SuccessCode.POST_READ_OK,postService.getPost(postId));
    }


    @Override
    @GetMapping
    public ApiResponse<List<PostResponseDto.ReadPostList>> getPostList() {
        return ApiResponse.success(SuccessCode.POST_LIST_READ_OK,postService.getPostList());
    }


    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<PostResponseDto.CreatePost> createPost(
            @Valid @RequestBody PostRequestDto.PostCreateReq request,
            @RequestParam Long userId
    ) {
        return ApiResponse.success(SuccessCode.POST_CREATED,postService.createPost(request,userId));
    }

    @Override
    @PatchMapping("/{postId}")
    public ApiResponse<PostResponseDto.UpdatePost> updatePost(
            @Valid @RequestBody PostRequestDto.PostUpdateReq request,
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        return ApiResponse.success(SuccessCode.POST_UPDATE_OK,postService.updatePost(request,postId,userId));
    }


    @Override
    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        postService.deletePost(postId,userId);
        return ApiResponse.success(SuccessCode.POST_DELETE_OK);
    }

}
