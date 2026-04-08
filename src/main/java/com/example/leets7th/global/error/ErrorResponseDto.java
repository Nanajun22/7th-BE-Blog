    package com.example.leets7th.global.error;




    public class ErrorResponseDto {
        public record ValidationErrorDto (String field,String value,String reason) {}
    }
