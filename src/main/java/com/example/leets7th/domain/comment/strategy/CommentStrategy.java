package com.example.leets7th.domain.comment.strategy;

import com.example.leets7th.domain.comment.domain.Comment;
import com.example.leets7th.domain.comment.domain.CommentRepository;
import com.example.leets7th.domain.comment.exception.CommentException;
import com.example.leets7th.domain.post.exception.PostException;
import com.example.leets7th.domain.report.domain.ReportContentType;
import com.example.leets7th.domain.report.strategy.ReportStrategy;
import com.example.leets7th.global.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentStrategy implements ReportStrategy {
    private final CommentRepository commentRepository;

    public boolean isValidType(ReportContentType contentType) {
        return ReportContentType.COMMENT == contentType;
    }

    public Long reportedUserId(Long commentId) {
        return commentRepository
                .findById(commentId)
                .orElseThrow(()-> new PostException(ErrorCode.COMMENT_NOT_FOUND))
                .getUser()
                .getId();
    }

    public void approve(Long commentId) {
        //댓글 삭제
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(()-> new CommentException(ErrorCode.COMMENT_NOT_FOUND));

        commentRepository.delete(comment);


        //대댓글 삭제
        commentRepository
                .softDeleteAllByRootCommentId(commentId);
    }
}
