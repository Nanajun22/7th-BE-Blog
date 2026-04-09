package com.example.leets7th.domain.post.Service;


import com.example.leets7th.domain.comment.service.CommentService;
import com.example.leets7th.domain.post.domain.Post;
import com.example.leets7th.domain.post.domain.PostRepository;
import com.example.leets7th.domain.post.dto.PostRequestDto;
import com.example.leets7th.domain.post.dto.PostResponseDto;


import com.example.leets7th.domain.user.service.UserService;
import com.example.leets7th.global.code.ErrorCode;
import com.example.leets7th.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final CommentService commentService;


    public PostResponseDto.ReadPost getPost(Long postId) {
        Post post = postRepository.findByIdWithUser(postId).orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));
        return PostResponseDto.ReadPost.from(post);
    }

    public List<PostResponseDto.ReadPostList> getPostList() {
        return postRepository.findPostListWithUser()
                .stream()
                .map(PostResponseDto.ReadPostList::from)
                .toList();
    }

    @Transactional
    public PostResponseDto.CreatePost createPost(PostRequestDto.Create request,Long userId) {
        Post post = Post.create(request.title(),request.content(),userService.getUser(userId));
        postRepository.save(post);

        return PostResponseDto.CreatePost.from(post);
    }

    @Transactional
    public PostResponseDto.UpdatePost updatePost(PostRequestDto.Update request,Long postId,Long userId) {
        Post post = postRepository.findByIdWithUser(postId).orElseThrow(()-> new GlobalException(ErrorCode.POST_NOT_FOUND));

        if(!userId.equals(post.getUser().getId())) {
            throw new GlobalException(ErrorCode.POST_UPDATE_NO_PERMISSION);
        }

        post.modifyPost(request.title(), request.content());
        return PostResponseDto.UpdatePost.from(post);
    }

    @Transactional
    public void deletePost(Long postId,Long userId) {
        Post post = postRepository.findByIdWithUser(postId).orElseThrow(()-> new GlobalException(ErrorCode.POST_NOT_FOUND));

        if(!userId.equals(post.getUser().getId())){
            throw new GlobalException(ErrorCode.POST_DELETE_NO_PERMISSION);
        }
        commentService.deleteAllCommentByPost(postId);
        postRepository.delete(post);
    }



}
