package com.example.leets7th.domain.report.dto;

import com.example.leets7th.domain.report.domain.ReportContentType;
import com.example.leets7th.domain.report.domain.ReportStatus;

public class ReportRequestDto {
    public record ReportCreateReq(String reason,ReportContentType contentType,Long contentId) {}

    public record ReportListReq(ReportStatus status) {}
}
