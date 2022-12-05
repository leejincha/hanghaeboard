package com.sparta.hanghaeboard.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostRequestDto {

    private String username;
    private String content;
    private String title;
    private String password;
    private Long id;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}

