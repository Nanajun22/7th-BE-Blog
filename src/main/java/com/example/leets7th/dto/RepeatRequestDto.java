package com.example.demo.dto;


import jakarta.validation.constraints.NotBlank;



public record RepeatRequestDto (@NotBlank(message = "내용을 입력해주세요.") String value){ }
