package com.example.leets7th.domain.post.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT p FROM Post p JOIN FETCH p.user")
    List<Post> findPostListWithUser();

    @Query("SELECT p FROM Post p JOIN FETCH p.user WHERE p.id = :postId")
    Optional<Post> findByIdWithUser(@Param("postId")Long postId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p SET p.deletedAt = CURRENT_TIMESTAMP WHERE p.user.id = : uid")
    int softDeleteAllByUserId(@Param("uid")Long uid);


}
