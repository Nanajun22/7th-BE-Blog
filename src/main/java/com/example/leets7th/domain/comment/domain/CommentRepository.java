package com.example.leets7th.domain.comment.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CommentRepository extends JpaRepository<Comment,Long> {


    //포스트 댓글 삭제
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Comment c SET c.deletedAt = CURRENT_TIMESTAMP WHERE c.post.id = :pid")
    int softDeleteAllByPostId(@Param("pid") Long pid);


    //유저 댓글 삭제
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Comment c SET c.deletedAt = CURRENT_TIMESTAMP WHERE c.user.id = :uid")
    int softDeleteAllByUserId(@Param("uid") Long uid);


    //대댓글 삭제
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Comment c SET c.deletedAt = CURRENT_TIMESTAMP WHERE c.rootComment.id = :rootCommentId")
    void softDeleteAllByRootCommentId(@Param("rootCommentId")Long rootCommentId);


}
