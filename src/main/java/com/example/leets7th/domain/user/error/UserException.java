package com.example.leets7th.domain.user.error;

import com.example.leets7th.global.common.BaseCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private final BaseCode baseCode;

    public UserException(BaseCode baseCode) {
        super(baseCode.getMessage());
        this.baseCode = baseCode;
    }
}
