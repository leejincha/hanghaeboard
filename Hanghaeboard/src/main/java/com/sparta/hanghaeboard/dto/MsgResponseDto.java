package com.sparta.hanghaeboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MsgResponseDto {
    private int statusCode;
    private String msg;
}
