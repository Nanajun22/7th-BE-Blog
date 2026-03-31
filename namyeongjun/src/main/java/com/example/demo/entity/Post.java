package com.example.demo.entity;


import com.example.demo.entity.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.envers.Audited;


@Entity @Table(name = "posts", indexes = {
        @Index(name = "idx_posts_deleted_at", columnList = "deleted_at")
})
@SQLDelete(sql = "UPDATE posts SET deleted_at = NOW() WHERE posts_id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "content", nullable = false,columnDefinition = "TEXT")
    private String content;






    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Builder
    private Post(String title,String content,User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void modifyPost(String title,String content) {
        this.title = title;
        this.content = content;
    }




}
