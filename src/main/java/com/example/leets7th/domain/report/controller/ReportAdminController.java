package com.example.leets7th.domain.report.controller;

import com.example.leets7th.domain.report.domain.Report;
import com.example.leets7th.domain.report.domain.ReportContentType;
import com.example.leets7th.domain.report.domain.ReportStatus;
import com.example.leets7th.domain.report.dto.ReportRequestDto;
import com.example.leets7th.domain.report.dto.ReportResponseDto;
import com.example.leets7th.domain.report.service.ReportAdminService;
import com.example.leets7th.global.code.SuccessCode;
import com.example.leets7th.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin/reports")
@RestController
@RequiredArgsConstructor
public class ReportAdminController {
    private final ReportAdminService reportAdminService;

    //신고 목록 조회 API
    @GetMapping
    public ApiResponse<List<ReportResponseDto.ReportListRes>> getReportList(
            @RequestParam ReportStatus status
    ) {
        List<ReportResponseDto.ReportListRes> reports = reportAdminService.getReportList(status);
        return ApiResponse.success(SuccessCode.GENERAL_OK,reports);
    }

    //신고 승인 API
    @PostMapping("/{reportId}/approve")
    public ApiResponse<Void> approveReport(@PathVariable Long reportId) {
        reportAdminService.approveReport(reportId);
        return ApiResponse.success(SuccessCode.GENERAL_OK);
    }


    //신고 반려 API
    @PostMapping("/{reportId}/reject")
    public ApiResponse<Void> rejectReport(@PathVariable Long reportId) {
        reportAdminService.rejectReport(reportId);
        return ApiResponse.success(SuccessCode.GENERAL_OK);
    }


}
