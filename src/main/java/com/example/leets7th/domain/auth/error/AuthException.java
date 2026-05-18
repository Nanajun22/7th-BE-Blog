package com.example.leets7th.domain.auth.error;

import com.example.leets7th.global.common.BaseCode;
import com.example.leets7th.global.error.GlobalException;


public class AuthException extends GlobalException {
    public AuthException(BaseCode baseCode) {
        super(baseCode);
    }
}
