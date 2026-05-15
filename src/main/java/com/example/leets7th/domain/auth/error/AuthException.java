package com.example.leets7th.domain.auth.error;

import com.example.leets7th.global.common.BaseCode;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
    private final BaseCode baseCode;
    public AuthException(BaseCode baseCode) {
        super(baseCode.getMessage());
        this.baseCode = baseCode;
    }
}
