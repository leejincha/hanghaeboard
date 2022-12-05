package com.sparta.hanghaeboard.dto;

import lombok.Getter;

@Getter
public class DelResponseDto {
    private String statusCode;
    private String message;

    public void setResult(String statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }

}
