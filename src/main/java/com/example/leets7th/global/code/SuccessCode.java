package com.example.leets7th.global.code;

import com.example.leets7th.global.common.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum SuccessCode implements BaseCode {
    POST_LIST_READ_OK(HttpStatus.OK,"POST200_1","게시글 목록 조회에 성공하였습니다"),
    POST_READ_OK(HttpStatus.OK,"POST200_2","게시글 상세 조회에 성공하였습니다."),
    POST_CREATED(HttpStatus.CREATED,"POST201_1","게시글 작성에 성공하였습니다."),
    POST_UPDATE_OK(HttpStatus.OK,"POST200_3","게시글이 수정되었습니다."),
    POST_DELETE_OK(HttpStatus.OK,"POST200_4","게시글 삭제에 성공했습니다.");



    private final HttpStatus status;
    private final String code;
    private final String message;
}
