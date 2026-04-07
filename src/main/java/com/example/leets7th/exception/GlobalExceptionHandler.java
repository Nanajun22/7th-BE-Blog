package com.example.demo.exception;



import com.example.demo.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex) {

        List<ErrorResponseDto.ValidationErrorDto> validationErrors = new ArrayList<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()) {
            validationErrors.add(new ErrorResponseDto.ValidationErrorDto(error.getField(), error.getDefaultMessage()));
        }

        ErrorResponseDto errorReponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(),"Validation",validationErrors);

        return ResponseEntity.badRequest().body(errorReponseDto);
    }

}
