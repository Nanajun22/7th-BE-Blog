package com.example.leets7th.domain.report.dto;

import com.example.leets7th.domain.report.domain.ReportContentType;
import com.example.leets7th.domain.report.domain.ReportStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReportRequestDto {
    public record ReportCreateReq(
            @NotBlank(message = "신고 사유를 기재해주세요.")
            String reason,

            @NotNull(message = "컨텐츠 타입 미입력")
            ReportContentType contentType,

            @NotNull(message = "컨텐츠 아이디 미입력")
            Long contentId
    ) {

    }


}
