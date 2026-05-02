package com.example.leets7th.domain.post.strategy;

import com.example.leets7th.domain.post.domain.Post;
import com.example.leets7th.domain.post.domain.PostRepository;
import com.example.leets7th.domain.post.error.PostException;
import com.example.leets7th.domain.report.domain.ReportContentType;
import com.example.leets7th.domain.report.strategy.ReportStrategy;
import com.example.leets7th.global.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostReportStrategy implements ReportStrategy {

    private final PostRepository postRepository;

    //컨텐츠 타입 확인
    public boolean isValidType(ReportContentType reportContentType) {
        return ReportContentType.POST == reportContentType;
    }


    //신고 당한 유저 아이디 반환 && 컨텐츠 존재 여부 확인
    public Long reportedUserId(Long postId) {
        return postRepository
                .findById(postId)
                .orElseThrow(()-> new PostException(ErrorCode.POST_NOT_FOUND))
                .getUser()
                .getId();
    }

    //신고 승인 시 게시글 삭제
    public void approve(Long postId) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));
        postRepository.delete(post);
    }



}
