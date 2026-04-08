package com.example.leets7th.global.error;

import com.example.leets7th.global.common.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{
    private final BaseCode baseCode;

    public GlobalException(BaseCode baseCode) {
        super(baseCode.getMessage());
        this.baseCode =baseCode;
    }
}
