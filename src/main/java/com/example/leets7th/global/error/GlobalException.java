package com.example.leets7th.global.error;

import com.example.leets7th.global.common.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException{
    private final BaseCode baseCode;
}
