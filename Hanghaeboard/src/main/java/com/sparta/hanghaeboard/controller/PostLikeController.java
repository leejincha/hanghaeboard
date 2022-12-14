package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.StatusCodeDto;
import com.sparta.hanghaeboard.security.UserDetailsImpl;
import com.sparta.hanghaeboard.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/api/post-likes/{id}")
    public StatusCodeDto postLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postLikeService.postLike(id, userDetails.getUser());
    }
}
