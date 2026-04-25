package com.example.leets7th.domain.report.domain;

import com.example.leets7th.global.common.BaseTimeEntity;
import com.example.leets7th.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity @Table(name = "reports")
public class Report extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;


    @Column(name = "reason",columnDefinition = "TEXT")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name ="status",nullable = false,length = 50)
    private ReportStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type",nullable = false,length = 50)
    private ReportContentType contentType;

    @Column(name = "content_id",nullable = false)
    private Long contentId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id",nullable = false)
    private User reporter;




    @Builder(access = AccessLevel.PRIVATE)
    private Report(String reason,ReportContentType contentType,Long contentId,User reporter) {
        this.reason = reason;
        this.contentType =contentType;
        this.contentId = contentId;
        this.reporter = reporter;
    }

    public static Report create(String reason,ReportContentType contentType,Long contentId,User reporter) {
        return Report.builder()
                .reason(reason)
                .contentType(contentType)
                .contentId(contentId)
                .reporter(reporter)
                .build();
    }

    public void changeStatus(ReportStatus status) {
        this.status = status;
    }


}
