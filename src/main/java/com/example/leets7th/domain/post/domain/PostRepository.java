package com.example.leets7th.domain.post.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("select p from Post p join fetch p.user")
    List<Post> findPostListWithUser();

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p SET p.deletedAt = CURRENT_TIMESTAMP WHERE p.user.id = : uid")
    int softDeleteAllByUserId(@Param("uid")Long uid);


}
