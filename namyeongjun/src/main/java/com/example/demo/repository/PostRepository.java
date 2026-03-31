package com.example.demo.repository;

import com.example.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post,Long> {


    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p SET p.deletedAt = CURRENT_TIMESTAMP WHERE p.user.id = : uid")
    int softDeleteAllByUserId(@Param("uid")Long uid);


}
