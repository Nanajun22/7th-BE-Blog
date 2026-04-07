package com.example.demo.entity;



import com.example.demo.entity.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;


@Entity @Table(name = "users",indexes = {
        @Index(name = "idx_users_deleted_at",columnList = "deleted_at")
})
@Getter
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW(),email =CONCAT(email,'_',UUID()),login_id =CONCAT(login_id,'_',UUID()) WHERE user_id = ? ")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name ="login_id",nullable = false,unique = true,length = 100)
    private String loginId;

    @Column(name = "name",nullable = false, length = 50)
    private String name;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "email",unique = true, nullable = false, length = 100)
    private String email;

    @Builder
    private User(String loginId,String name,String password,String email) {
        this.loginId =loginId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateName(String name) {
        this.name = name;
    }


}
