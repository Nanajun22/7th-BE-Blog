package com.example.leets7th.domain.auth.service;


import com.example.leets7th.domain.auth.dto.MailRequestDto;
import com.example.leets7th.domain.auth.dto.MailResponseDto;
import com.example.leets7th.domain.auth.error.MailException;
import com.example.leets7th.domain.auth.repository.MailCacheRepository;
import com.example.leets7th.global.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailCacheRepository mailCacheRepository;

    // 메일 전송 비동기 처리
    @Async
    public void sendMail(MailRequestDto.send request) {
        String code = createRandomCode();

        // 기존 데이터 삭제
        mailCacheRepository.deleteAuthCode(request.email());
        mailCacheRepository.deleteVerifyToken(request.email());

        //인증 코드 저장
        mailCacheRepository.saveAuthCode(request.email(),code);

        //메일 전송

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(request.email());
        mail.setSubject("인증번호 코드 발송[Leets]");
        mail.setText("인증번호:"+code);


        // <!!!!!!!!!!!!!!!!!!메일 전송 실패시 비동기 스레드에서 예외처리 구현해야함!!!!!!>
        javaMailSender.send(mail);
    }

    //인증 코드 검증 메서드
    public MailResponseDto.Verify verifyAuthCode(MailRequestDto.verify request) {

        // 인증 코드 비교
        if(!request.authCode()
                .equals(mailCacheRepository.getAuthCode(request.email()))
        ) {
            mailCacheRepository.deleteAuthCode(request.email());
            throw new MailException(ErrorCode.AUTH_CODE_NOT_EQUAL);
        }


        //인증 코드 정보 삭제 && 기존 인증토큰 삭제
        mailCacheRepository.deleteAuthCode(request.email());
        mailCacheRepository.deleteVerifyToken(request.email());

        // 인증 토큰 저장
        String verifiedToken = UUID.randomUUID().toString();
        mailCacheRepository.saveVerifyToken(request.email(),verifiedToken);

        return new MailResponseDto.Verify(verifiedToken);
    }


    //인증 코드 생성 메서드
    private String createRandomCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(899999) + 100000);
    }
}
