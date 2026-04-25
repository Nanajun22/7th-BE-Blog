package com.example.leets7th.domain.report.strategy;


import com.example.leets7th.domain.report.domain.ReportContentType;

//전략 패턴 공통 인터페이스
public interface ReportStrategy {

    //타입 판단
    boolean isValidType(ReportContentType reportContentType);


    //승인 도메인 처리
    void approve(Long postId);


}
