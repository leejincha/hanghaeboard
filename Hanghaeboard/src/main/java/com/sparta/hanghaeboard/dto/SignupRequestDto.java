package com.sparta.hanghaeboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor //Entity Class를 프로젝트 코드상에서 기본생성자로 생성하는 것은 금지하고, JPA에서 Entity 클래스를 생성하는것은 허용하기 위해 추가한다.
@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자를 추가한다.
public class SignupRequestDto {
    @Size(min = 4,max = 10,message ="아이디의 길이는 4자에서 10자 사이입니다")
    @Pattern(regexp = "[a-z0-9]*$",message = "아이디 형식이 일치하지 않습니다")
    private String username;

    @Size(min = 8,max = 15,message ="비밀번호의 길이는 8자에서 15자 사이입니다")
    @Pattern(regexp = "[a-zA-Z0-9`~!@#$%^&*()_=+|{};:,.<>/?]*$",message = "비밀번호 형식이 일치하지 않습니다")
    private String password;
    private boolean admin = false;
    private String adminToken = "";

}