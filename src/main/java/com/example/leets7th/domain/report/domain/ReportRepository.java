package com.example.leets7th.domain.report.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Long> {
    //데이터 존재 여부 파악
    boolean existsByReporterIdAndContentTypeAndContentId(
            Long reporterId,
            ReportContentType contentType,
            Long contentId
    );

    long countByContentTypeAndContentId(ReportContentType contentType,Long contentId);


    //패치조인 목록 조회 쿼리
    @Query("SELECT r FROM Report r JOIN FETCH r.reporter WHERE r.status = :status")
    List<Report> findByStatusWithReporter(ReportStatus status);


}
