package com.sparta.hanghaeboard.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    COMMON_BAD_REQUEST_400(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    아이디가_일치하지_않습니다(HttpStatus.BAD_REQUEST, "아이디가 일치하지 않습니다."),
    중복_사용자_존재_400(HttpStatus.BAD_REQUEST, "중복된 아이디 입니다."),
    비밀번호가_일치하지_않습니다_400(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    관리자_비밀번호가_일치하지_않습니다_400(HttpStatus.BAD_REQUEST, "관리자 비밀번호가 일치하지 않습니다."),
    게시글이_존재하지_않습니다_400(HttpStatus.BAD_REQUEST, "게시글이 존재하지 않습니다."),
    댓글이_존재하지_않습니다_400(HttpStatus.BAD_REQUEST, "댓글이 존재하지 않습니다."),
    유효하지_않은_토큰_400(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
    작성자만_삭제할_수_있습니다_400(HttpStatus.BAD_REQUEST, "작성자만 삭제할 수 있습니다."),
    작성자만_수정할_수_있습니다_400(HttpStatus.BAD_REQUEST, "작성자만 수정할 수 있습니다."),
    중복된_아이디_입니다_400(HttpStatus.BAD_REQUEST, "중복된 아이디 입니다."),
    회원을_찾을_수_없습니다_400(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
