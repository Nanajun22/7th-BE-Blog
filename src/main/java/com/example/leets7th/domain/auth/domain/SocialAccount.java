package com.example.leets7th.domain.auth.domain;

import com.example.leets7th.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class SocialAccount {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_account_id")
    private Long id;

    @JoinColumn(name = "user_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider",nullable = false)
    private OAuthProvider provider;

    @Column(name = "provider_id")
    private String providerId;

    private SocialAccount(User user,OAuthProvider provider,String providerId) {
        this.user = user;
        this.provider =provider;
        this.providerId = providerId;
    }


    public static SocialAccount create(User user,OAuthProvider provider,String providerId) {
        return new SocialAccount(user,provider,providerId);
    }


}
