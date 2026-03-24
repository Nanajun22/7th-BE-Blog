    package com.example.demo.dto;

    import com.fasterxml.jackson.annotation.JsonInclude;
    import lombok.Getter;

    import java.util.List;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ErrorResponseDto (int status, String message, List<ValidationErrorDto> validation) {

        public ErrorResponseDto(int status,String message) {
            this(status,message,null);
        }

        public record ValidationErrorDto (String field,String message) {}

    }
