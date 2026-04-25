package com.example.leets7th.domain.report.dto;

import com.example.leets7th.domain.report.domain.Report;
import com.example.leets7th.domain.report.domain.ReportContentType;
import com.example.leets7th.domain.report.domain.ReportStatus;

public class ReportResponseDto {

    public record ReportCreateRes(String reason,
                                  ReportContentType contentType,
                                  Long contentId
    ) {
       public static ReportCreateRes from(Report report) {
           return new ReportCreateRes(
                   report.getReason(),
                   report.getContentType(),
                   report.getContentId()
           );
       }

    }


    public record ReportListRes(
            Long reportId,
            ReportContentType contentType,
            Long contentId,
            ReportStatus status,
            String reporterName
            ) {

        public static ReportListRes from(Report report) {
            return new ReportListRes(
                    report.getId(),
                    report.getContentType(),
                    report.getContentId(),
                    report.getStatus(),
                    report.getReporter().getName()
            );
        }

    }

}
