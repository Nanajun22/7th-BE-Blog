package com.example.demo.entity;

import com.example.demo.entity.common.BaseTimeEntity;
import com.example.demo.entity.common.ReportContentType;
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


    @Column(name = "report_reason",columnDefinition = "TEXT")
    private String reportReason;


    @Enumerated(EnumType.STRING)
    @Column(name = "content_type",nullable = false,length = 50)
    private ReportContentType contentType;

    @Column(name = "content_id",nullable = false)
    private Long contentId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User reporter;




    @Builder
    private Report(String reportReason,ReportContentType contentType,Long contentId,User reporter) {
        this.reportReason = reportReason;
        this.contentType =contentType;
        this.contentId = contentId;
        this.reporter = reporter;
    }



}
