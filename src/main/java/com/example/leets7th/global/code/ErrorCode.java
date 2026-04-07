package com.example.leets7th.global.code;

import com.example.leets7th.global.common.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements BaseCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COMMON500_1","예기치 않은 서버 에러가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400_1","잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"AUTH401_1","인증이 필요합니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN,"AUTH403_1","요청이 거부되었습니다."),

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST,"COMMON400_3","입력값이 유효하지 않습니다."),


    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"USER404_1","존재하지 않는 유저입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
