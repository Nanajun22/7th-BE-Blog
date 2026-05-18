package com.example.leets7th.domain.auth.error;

import com.example.leets7th.global.common.BaseCode;
import com.example.leets7th.global.error.GlobalException;
import lombok.Getter;


public class MailException extends GlobalException {

    public MailException(BaseCode baseCode) {
        super(baseCode);
    }
}
