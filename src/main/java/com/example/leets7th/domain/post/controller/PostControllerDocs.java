package com.example.leets7th.domain.post.controller;


import com.example.leets7th.domain.post.dto.PostRequestDto;
import com.example.leets7th.domain.post.dto.PostResponseDto;
import com.example.leets7th.global.annotation.ApiErrorResponse;
import com.example.leets7th.global.code.ErrorCode;
import com.example.leets7th.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post",description = "게시글 API")
public interface PostControllerDocs {


    @Operation(summary = "게시글 상세 조회", description = "특정 게시글의 상세정보를 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "게시글 상세 조회에 성공하였습니다.")
    @ApiErrorResponse({ErrorCode.POST_NOT_FOUND})
    ApiResponse<PostResponseDto.ReadPost> getPost(
            Long postId,
            Long userId
    );

    @Operation(summary = "게시글 목록 조회",description = "게시글 전체 목록을 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "게시글 목록 조회에 성공하였습니다.")
    ApiResponse<List<PostResponseDto.ReadPostList>> getPostList();


    @Operation(summary = "게시글 생성",description = "새로운 게시글을 생성합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "게시글 작성에 성공하였습니다.")
    @ApiErrorResponse({
            ErrorCode.USER_NOT_FOUND,
            ErrorCode.POST_INPUT_NO_VALIDATION
    })
    ApiResponse<PostResponseDto.CreatePost> createPost(
            PostRequestDto.PostCreateReq request,
            Long userId
    );

    @Operation(summary = "게시글 수정", description = "게시글 제목과 내용을 수정합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 수정에 성공하였습니다.")
    @ApiErrorResponse({
            ErrorCode.POST_UPDATE_NO_PERMISSION,
            ErrorCode.POST_NOT_FOUND,
            ErrorCode.POST_INPUT_NO_VALIDATION
    })
    ApiResponse<PostResponseDto.UpdatePost> updatePost(
            PostRequestDto.PostUpdateReq request,
            Long postId,
            Long userId
    );



    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 삭제에 성공하였습니다.")
    @ApiErrorResponse({
            ErrorCode.POST_DELETE_NO_PERMISSION,
            ErrorCode.POST_NOT_FOUND
    })
    ApiResponse<Void> deletePost(
            Long postId,
            Long userId
    );


}
