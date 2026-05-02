package com.example.leets7th.domain.report.controller;


import com.example.leets7th.domain.report.dto.ReportRequestDto;
import com.example.leets7th.domain.report.dto.ReportResponseDto;
import com.example.leets7th.global.annotation.ApiErrorResponse;
import com.example.leets7th.global.code.ErrorCode;
import com.example.leets7th.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Report",description = "신고 API")
public interface ReportControllerDocs {



    @Operation(summary = "신고 생성",description = "새로운 신고를 접수합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201",description = "신고가 접수되었습니다.")
    @ApiErrorResponse({
            ErrorCode.SELF_REPORT,
            ErrorCode.REPORT_ALREADY_EXIST,
            ErrorCode.UNSUPPORTED_CONTENT_TYPE
    })
    ApiResponse<ReportResponseDto.ReportCreateRes> createReport(
            ReportRequestDto.ReportCreateReq request,
            Long userId
    );




    @Operation(summary = "신고 삭제",description = "신고를 취소합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "신고가 취소되었습니다.")
    @ApiErrorResponse({
            ErrorCode.REPORT_NOT_FOUND,
            ErrorCode.REPORT_DELETE_NO_PERMISSION,
            ErrorCode.REPORT_ALREADY_HANDLED
    })
    ApiResponse<Void> deleteReport(
            Long reportId,
            Long userId
    );

}
