package com.example.leets7th.domain.auth.controller;

import com.example.leets7th.domain.auth.dto.MailRequestDto;
import com.example.leets7th.domain.auth.dto.MailResponseDto;
import com.example.leets7th.domain.auth.service.MailService;
import com.example.leets7th.global.code.SuccessCode;
import com.example.leets7th.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    public ApiResponse<Void> sendMail(@RequestBody @Valid MailRequestDto.send request) {
        mailService.sendMail(request);
        return ApiResponse.success(SuccessCode.GENERAL_OK);
    }



    @PostMapping("/verify")
    public ApiResponse<MailResponseDto.Verify> verifyMail(@RequestBody @Valid MailRequestDto.verify request) {
        return ApiResponse.success(SuccessCode.GENERAL_OK,mailService.verifyAuthCode(request));
    }
}
