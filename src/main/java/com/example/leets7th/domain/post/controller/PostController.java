package com.example.leets7th.domain.post.controller;

import com.example.leets7th.domain.post.Service.PostService;
import com.example.leets7th.domain.post.dto.PostRequestDto;
import com.example.leets7th.domain.post.dto.PostResponseDto;
import com.example.leets7th.global.code.SuccessCode;
import com.example.leets7th.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController implements PostControllerDocs {
    private final PostService postService;


    @Override
    public ApiResponse<PostResponseDto.ReadPost> getPost(
            Long postId,
            Long userId
    ) {
        return ApiResponse.success(SuccessCode.POST_READ_OK,postService.getPost(postId));
    }


    @Override
    public ApiResponse<List<PostResponseDto.ReadPostList>> getPostList() {
        return ApiResponse.success(SuccessCode.POST_LIST_READ_OK,postService.getPostList());
    }


    @Override
    public ApiResponse<PostResponseDto.CreatePost> createPost(
            PostRequestDto.Create request,
            Long userId
    ) {
        return ApiResponse.success(SuccessCode.POST_CREATED,postService.createPost(request,userId));
    }

    @Override
    public ApiResponse<PostResponseDto.UpdatePost> updatePost(
            PostRequestDto.Update request,
            Long postId,
            Long userId
    ) {
        return ApiResponse.success(SuccessCode.POST_UPDATE_OK,postService.updatePost(request,postId,userId));
    }


    @Override
    public ApiResponse<Void> deletePost(Long postId,
                                        Long userId
    ) {
        postService.deletePost(postId,userId);
        return ApiResponse.success(SuccessCode.POST_DELETE_OK);
    }

}
