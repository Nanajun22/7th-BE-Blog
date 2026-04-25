package com.example.leets7th.domain.report.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Long> {
    boolean existsByReporterIdAndContentTypeAndContentId(
            Long reporterId,
            ReportContentType contentType,
            Long contentId
    );


    @Query("SELECT r FROM Report r JOIN FETCH r.reporter WHERE r.status = :status")
    List<Report> findByStatusWithReporter(ReportStatus status);
}
