package com.example.leets7th.domain.report.service;



import com.example.leets7th.domain.report.domain.Report;
import com.example.leets7th.domain.report.domain.ReportContentType;
import com.example.leets7th.domain.report.domain.ReportRepository;
import com.example.leets7th.domain.report.domain.ReportStatus;
import com.example.leets7th.domain.report.dto.ReportRequestDto;
import com.example.leets7th.domain.report.dto.ReportResponseDto;
import com.example.leets7th.domain.report.error.ReportException;
import com.example.leets7th.domain.report.strategy.ReportStrategy;
import com.example.leets7th.domain.user.service.UserService;
import com.example.leets7th.global.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReportService {
    private static final int REPORT_THRESHOLD = 10;

    private final ReportRepository reportRepository;
    private final UserService userService;

    private final List<ReportStrategy> strategyList;

    //신고 생성 로직
    @Transactional
    public ReportResponseDto.ReportCreateRes createReport(
            ReportRequestDto.ReportCreateReq request,
            Long reporterId
    ) {

        //중복 체크
        if(reportRepository.existsByReporterIdAndContentTypeAndContentId(
                reporterId,
                request.contentType(),
                request.contentId()
        )) {
            throw new ReportException(ErrorCode.REPORT_ALREADY_EXIST);
        }


        // 자기자신 신고 방지 && 컨텐츠 존재 검증
        ReportStrategy strategy = findStrategy(request.contentType());

        if(strategy.reportedUserId(request.contentId()).equals(reporterId)) {
            throw new ReportException(ErrorCode.SELF_REPORT);
        }


        // 신고 생성
        Report report = Report.create(
                request.reason(),
                request.contentType(),
                request.contentId(),
                userService.getUser(reporterId)
        );



        //일정 횟수 이상 자동 신고 승인
        if(isExceedThreshold(request.contentType(),request.contentId())) {
            report.changeStatus(ReportStatus.APPROVED);
            strategy.approve(request.contentId());
        }

        //DB 저장
        reportRepository.save(report);

        return ReportResponseDto.ReportCreateRes.from(report);

    }





    //신고 삭제 로직
    @Transactional
    public void deleteReport(Long reportId,Long userId) {
        //신고 존재 여부 검증
        Report report = reportRepository
                .findById(reportId)
                .orElseThrow(()->new ReportException(ErrorCode.REPORT_NOT_FOUND));

        //신고자와 사용자 일치 검증
        if(!report.getReporter().getId().equals(userId)) {
            throw new ReportException(ErrorCode.REPORT_DELETE_NO_PERMISSION);
        }

        //처리된 신고 삭제 불가
        if(report.getStatus() != ReportStatus.PENDING) {
            throw new ReportException(ErrorCode.REPORT_ALREADY_HANDLED);
        }

        reportRepository.delete(report);
    }





    //신고횟수 확인 메서드
    private boolean isExceedThreshold(ReportContentType contentType,Long contentId) {
        long count = reportRepository.countByContentTypeAndContentId(contentType,contentId);

        return count+1 >=REPORT_THRESHOLD;
    }


    // 전략 클래스 탐색 메서드
    private ReportStrategy findStrategy(ReportContentType contentType) {
        return strategyList
                .stream()
                .filter((s)->s.isValidType(contentType))
                .findFirst()
                .orElseThrow(()-> new ReportException(ErrorCode.UNSUPPORTED_CONTENT_TYPE));
    }


}
