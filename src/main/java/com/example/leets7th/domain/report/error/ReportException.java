package com.example.leets7th.domain.report.error;

import com.example.leets7th.global.common.BaseCode;
import lombok.Getter;

@Getter
public class ReportException extends RuntimeException {
    BaseCode baseCode;
    public ReportException(BaseCode baseCode) {
        super(baseCode.getMessage());
        this.baseCode =baseCode;
    }
}
