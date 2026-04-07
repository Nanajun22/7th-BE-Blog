package com.example.demo.entity;


import com.example.demo.entity.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.envers.Audited;


@Entity @Table(name = "comments",indexes = {
        @Index(name = "idx_comments_deleted_at",columnList = "deleted_at")
})
@SQLDelete(sql = "UPDATE comments SET deleted_at = NOW() WHERE comment_id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "content",nullable = false,columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_comment_id")
    private Comment rootComment;


    @Builder
    private Comment(String content,User user,Post post,Comment rootComment) {
        this.content =content;
        this.user = user;
        this.post = post;
        this.rootComment = rootComment;
    }

    public void modifyComment(String content) {
        this.content = content;
    }
//

}
