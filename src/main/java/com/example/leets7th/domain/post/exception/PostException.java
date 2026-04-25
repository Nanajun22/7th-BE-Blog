package com.example.leets7th.domain.post.exception;

import com.example.leets7th.global.common.BaseCode;
import lombok.Getter;


@Getter
public class PostException extends RuntimeException{
    private final BaseCode baseCode;

    public PostException(BaseCode baseCode) {
        super(baseCode.getMessage());
        this.baseCode = baseCode;
    }

}
