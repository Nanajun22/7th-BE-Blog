package com.example.leets7th.domain.report.controller;



import com.example.leets7th.domain.report.dto.ReportRequestDto;
import com.example.leets7th.domain.report.dto.ReportResponseDto;
import com.example.leets7th.domain.report.service.ReportService;
import com.example.leets7th.global.code.SuccessCode;
import com.example.leets7th.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController implements ReportControllerDocs {

    private final ReportService reportService;

    //신고 접수 API
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<ReportResponseDto.ReportCreateRes> createReport(
            @Valid @RequestBody ReportRequestDto.ReportCreateReq request,
            @RequestParam Long userId
    ) {
        ReportResponseDto.ReportCreateRes response = reportService.createReport(request,userId);
        return ApiResponse.success(SuccessCode.GENERAL_CREATED,response);
    }


    //신고 취소 API
    @Override
    @DeleteMapping("/{reportId}")
    public ApiResponse<Void> deleteReport(
            @PathVariable Long reportId,
            @RequestParam Long userId
    ) {
        reportService.deleteReport(reportId,userId);
        return ApiResponse.success(SuccessCode.GENERAL_OK);
    }



}
