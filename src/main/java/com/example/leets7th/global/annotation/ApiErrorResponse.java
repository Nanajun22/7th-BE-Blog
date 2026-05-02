package com.example.leets7th.global.annotation;



import com.example.leets7th.global.code.ErrorCode;
import com.example.leets7th.global.common.BaseCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorResponse {
    ErrorCode[] value();
}
