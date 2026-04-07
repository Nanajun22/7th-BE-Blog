package com.example.demo.repository;

import com.example.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;


public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Comment c SET c.deletedAt = CURRENT_TIMESTAMP WHERE c.post.id = :pid")
    int softDeleteAllByPostId(@Param("pid") Long pid);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Comment c SET c.deletedAt = CURRENT_TIMESTAMP WHERE c.user.id = :uid")
    int softDeleteAllByUserId(@Param("uid") Long uid);



}
