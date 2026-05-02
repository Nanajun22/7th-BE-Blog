package com.example.leets7th.domain.report.controller;


import com.example.leets7th.domain.report.domain.ReportStatus;
import com.example.leets7th.domain.report.dto.ReportResponseDto;
import com.example.leets7th.global.annotation.ApiErrorResponse;
import com.example.leets7th.global.code.ErrorCode;
import com.example.leets7th.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;

@Tag(name = "ReportAdmin" ,description = "신고 관리자 API")
public interface ReportAdminControllerDocs {


    @Operation(summary = "신고 목록 조회",description = "상태별 신고 목록을 확인합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "신고 목록 조회를 성공하였습니다.")
    ApiResponse<List<ReportResponseDto.ReportListRes>> getReportList(ReportStatus status);



    @Operation(summary = "신고 승인",description = "접수된 신고를 승인합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "신고가 승인되었습니다.")
    @ApiErrorResponse({
            ErrorCode.REPORT_NOT_FOUND,
            ErrorCode.UNSUPPORTED_CONTENT_TYPE
    })
    ApiResponse<Void> approveReport(Long reportId);



    @Operation(summary = "신고 반려",description = "접수된 신고를 반려합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "신고가 반려되었습니다.")
    @ApiErrorResponse({
            ErrorCode.REPORT_NOT_FOUND
    })
    ApiResponse<Void> rejectReport(Long reportId);



}
