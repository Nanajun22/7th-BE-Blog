package com.example.leets7th.domain.comment.service;

import com.example.leets7th.domain.comment.domain.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public void deleteAllCommentByPost(Long postId) {
        commentRepository.softDeleteAllByPostId(postId);
    }

}
