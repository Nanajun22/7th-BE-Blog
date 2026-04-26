package com.example.leets7th.domain.report.service;

import com.example.leets7th.domain.post.domain.Post;
import com.example.leets7th.domain.post.dto.PostResponseDto;
import com.example.leets7th.domain.report.domain.Report;
import com.example.leets7th.domain.report.domain.ReportContentType;
import com.example.leets7th.domain.report.domain.ReportRepository;
import com.example.leets7th.domain.report.domain.ReportStatus;
import com.example.leets7th.domain.report.dto.ReportRequestDto;
import com.example.leets7th.domain.report.dto.ReportResponseDto;
import com.example.leets7th.domain.report.error.ReportException;
import com.example.leets7th.domain.report.strategy.ReportStrategy;
import com.example.leets7th.global.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportAdminService {

    private final ReportRepository reportRepository;
    private final List<ReportStrategy> strategyList;


    //신고 목록 조회
    public List<ReportResponseDto.ReportListRes> getReportList(ReportStatus status) {
        return reportRepository
                .findByStatusWithReporter(status)
                .stream()
                .map(ReportResponseDto.ReportListRes::from)
                .toList();
    }



    //신고 승인 로직
    @Transactional
    public void approveReport(Long reportId) {

        //신고 승인 처리
        Report report = reportRepository
                .findById(reportId)
                .orElseThrow(()-> new ReportException(ErrorCode.REPORT_NOT_FOUND));

        report.changeStatus(ReportStatus.APPROVED);

        //도메인 승인 처리
        ReportStrategy strategy = findStrategy(report.getContentType());
        strategy.approve(report.getContentId());

    }

    //신고 반려 로직
    @Transactional
    public void rejectReport(Long reportId) {

        Report report = reportRepository
                .findById(reportId)
                .orElseThrow(()->new ReportException(ErrorCode.REPORT_NOT_FOUND));

        report.changeStatus(ReportStatus.REJECTED);

    }




    private ReportStrategy findStrategy(ReportContentType contentType) {
        return strategyList
                .stream()
                .filter((s)->s.isValidType(contentType))
                .findFirst()
                .orElseThrow(()-> new ReportException(ErrorCode.UNSUPPORTED_CONTENT_TYPE));
    }







}
