package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.MsgResponseDto;
import com.sparta.hanghaeboard.security.UserDetailsImpl;
import com.sparta.hanghaeboard.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/api/commentlikes/{id}")
    public MsgResponseDto commentLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.commentLike(id, userDetails.getUser());
    }
}
