package com.sparta.hanghaeboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자를 추가한다.
@NoArgsConstructor //Entity Class를 프로젝트 코드상에서 기본생성자로 생성하는 것은 금지하고, JPA에서 Entity 클래스를 생성하는것은 허용하기 위해 추가한다.
public class MsgResponseDto {

    private String message;
    private int statusCode;

}
