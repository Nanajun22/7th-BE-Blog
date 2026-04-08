package com.example.leets7th.domain.post.Service;


import com.example.leets7th.domain.comment.service.CommentService;
import com.example.leets7th.domain.post.domain.Post;
import com.example.leets7th.domain.post.domain.PostRepository;
import com.example.leets7th.domain.post.dto.PostRequestDto;
import com.example.leets7th.domain.post.dto.PostResponseDto;

import com.example.leets7th.domain.user.domain.User;
import com.example.leets7th.domain.user.service.UserService;
import com.example.leets7th.global.code.ErrorCode;
import com.example.leets7th.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final CommentService commentService;


    public PostResponseDto.ReadPost getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));


        return new PostResponseDto.ReadPost(post.getTitle(),
                post.getContent(),
                post.getUser().getName(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }

    public List<PostResponseDto.ReadPostList> getPostList() {
        List<Post> posts = postRepository.findPostListWithUser();

        List<PostResponseDto.ReadPostList> postDtos = new ArrayList<>();

        for(Post post : posts) {
            PostResponseDto.ReadPostList postDto = new PostResponseDto.ReadPostList(
                    post.getId(),
                    post.getTitle(),
                    post.getUser().getName(),
                    post.getCreatedAt(),
                    post.getUpdatedAt()
            );

            postDtos.add(postDto);

        }

        return postDtos;
    }

    @Transactional
    public PostResponseDto.CreatePost createPost(PostRequestDto.Create request,Long userId) {
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .user(userService.getUser(userId))
                .build();

        postRepository.save(post);

        return new PostResponseDto.CreatePost(post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getName(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }

    @Transactional
    public PostResponseDto.UpdatePost updatePost(PostRequestDto.Update request,Long postId,Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new GlobalException(ErrorCode.POST_NOT_FOUND));


        if(!userId.equals(post.getUser().getId())) {
            throw new GlobalException(ErrorCode.POST_UPDATE_NO_PERMISSION);
        }

        post.modifyPost(request.title(), request.content());


        return new PostResponseDto.UpdatePost(post.getTitle(),
                post.getContent(),
                post.getUser().getName(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }

    @Transactional
    public void deletePost(Long postId,Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new GlobalException(ErrorCode.POST_NOT_FOUND));

        if(!userId.equals(post.getUser().getId())){
            throw new GlobalException(ErrorCode.POST_DELETE_NO_PERMISSION);
        }
        commentService.deleteAllCommentByPost(postId);
        postRepository.delete(post);
    }



}
