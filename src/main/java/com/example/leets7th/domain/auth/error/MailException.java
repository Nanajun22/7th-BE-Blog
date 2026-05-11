package com.example.leets7th.domain.auth.error;

import com.example.leets7th.global.common.BaseCode;
import lombok.Getter;

@Getter
public class MailException extends RuntimeException{
    private final BaseCode baseCode;

    public MailException(BaseCode baseCode) {
        super(baseCode.getMessage());
        this.baseCode = baseCode;
    }
}
