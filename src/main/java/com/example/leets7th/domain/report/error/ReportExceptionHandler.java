package com.example.leets7th.domain.report.error;


import com.example.leets7th.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReportExceptionHandler {

    @ExceptionHandler(ReportException.class)
    public ResponseEntity<ApiResponse<Void>> handleReportException(ReportException ex) {
        return ResponseEntity.status(ex.getBaseCode().getStatus()).body(ApiResponse.failure(ex.getBaseCode()));
    }

}
