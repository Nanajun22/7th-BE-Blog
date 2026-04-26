package com.example.leets7th.global.code;

import com.example.leets7th.global.common.BaseCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ErrorCode implements BaseCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COMMON500_1","예기치 않은 서버 에러가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400_1","잘못된 요청입니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST,"COMMON400_3","입력값이 유효하지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"AUTH401_1","인증이 필요합니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN,"AUTH403_1","요청이 거부되었습니다."),



    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"USER404_1","존재하지 않는 유저입니다."),


    POST_INPUT_NO_VALIDATION(HttpStatus.BAD_REQUEST,"POST400_3","입력값이 유효하지 않습니다."),
    POST_UPDATE_NO_PERMISSION(HttpStatus.FORBIDDEN,"POST403_1","수정 권한이 없습니다."),
    POST_DELETE_NO_PERMISSION(HttpStatus.FORBIDDEN,"POST403_2","삭제 권한이 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"POST404_1","해당 게시글이 존재하지 않습니다."),


    REPORT_ALREADY_EXIST(HttpStatus.BAD_REQUEST,"REPORT400_1","이미 신고한 대상입니다."),
    SELF_REPORT(HttpStatus.BAD_REQUEST,"REPORT_400_2","신고할 수 없는 대상입니다."),
    REPORT_DELETE_NO_PERMISSION(HttpStatus.FORBIDDEN,"REPORT_403_1","취소 권한이 없습니다."),
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND,"REPORT404_1","해당 신고가 존재하지 않습니다."),
    UNSUPPORTED_CONTENT_TYPE(HttpStatus.NOT_FOUND,"REPORT404_2","지원하지 않는 대상입니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

}
