package com.example.leets7th.domain.post.domain;


import com.example.leets7th.domain.user.domain.User;
import com.example.leets7th.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;


@Entity @Table(name = "posts")
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
