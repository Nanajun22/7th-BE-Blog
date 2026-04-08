package com.example.leets7th.global.error;



import com.example.leets7th.global.code.ErrorCode;
import com.example.leets7th.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<ErrorResponseDto.ValidationErrorDto>>> handleValidationException(MethodArgumentNotValidException ex) {

        List<ErrorResponseDto.ValidationErrorDto> validationErrors = new ArrayList<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()) {
            validationErrors.add(new ErrorResponseDto.ValidationErrorDto(error.getField(),error.getRejectedValue().toString(),error.getDefaultMessage()));
        }


        return ResponseEntity.badRequest().body(ApiResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR,validationErrors));
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(GlobalException ex) {
        return ResponseEntity.status(ex.getBaseCode().getStatus()).body(ApiResponse.failure(ex.getBaseCode()));
    }

}
