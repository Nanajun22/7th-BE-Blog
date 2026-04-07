package com.example.leets7th.domain.post.code;

import com.example.leets7th.global.common.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements BaseCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"POST404_1","해당 게시글이 존재하지 않습니다.");




    private final HttpStatus status;
    private final String code;
    private final String message;

}
