package com.example.leets7th.domain.comment.exception;

import com.example.leets7th.global.common.BaseCode;
import lombok.Getter;


@Getter
public class CommentException extends RuntimeException {
    private final BaseCode baseCode;

    public CommentException(BaseCode baseCode) {
        super(baseCode.getMessage());
        this.baseCode = baseCode;
    }


}
