package com.example.leets7th.domain.report.service;



import com.example.leets7th.domain.comment.domain.Comment;
import com.example.leets7th.domain.comment.service.CommentService;
import com.example.leets7th.domain.post.Service.PostService;
import com.example.leets7th.domain.post.domain.Post;
import com.example.leets7th.domain.post.domain.PostRepository;
import com.example.leets7th.domain.post.dto.PostResponseDto;
import com.example.leets7th.domain.report.domain.Report;
import com.example.leets7th.domain.report.domain.ReportContentType;
import com.example.leets7th.domain.report.domain.ReportRepository;
import com.example.leets7th.domain.report.dto.ReportRequestDto;
import com.example.leets7th.domain.report.dto.ReportResponseDto;
import com.example.leets7th.domain.report.error.ReportException;
import com.example.leets7th.domain.user.service.UserService;
import com.example.leets7th.global.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReportService {
    private static final int REPORT_THRESHOLD = 10;

    private final ReportRepository reportRepository;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @Transactional
    public ReportResponseDto.ReportCreateRes createReport(
            ReportRequestDto.ReportCreateReq request,
            Long reporterId
    ) {

        //중복 체크 로직
        if(reportRepository.existsByReporterIdAndContentTypeAndContentId(
                reporterId,
                request.contentType(),
                request.contentId()
        )) {
            throw new ReportException(ErrorCode.REPORT_ALREADY_EXIST);
        }

        // 자기자신 신고 방지


        // 일정 횟수 이상 자동 블라인드


        //??????
        Report report = Report.create(
                request.reason(),
                request.contentType(),
                request.contentId(),
                userService.getUser(reporterId)
        );

        return ReportResponseDto.ReportCreateRes.from(report);

    }




}
