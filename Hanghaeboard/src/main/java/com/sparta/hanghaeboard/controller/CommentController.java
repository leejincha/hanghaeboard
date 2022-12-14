package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.CommentResponseDto;
import com.sparta.hanghaeboard.dto.CommentRequestDto;
import com.sparta.hanghaeboard.dto.StatusCodeDto;
import com.sparta.hanghaeboard.security.UserDetailsImpl;
import com.sparta.hanghaeboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/api/comment/{id}")
    public CommentResponseDto addComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 응답 보내기
        return commentService.addComment(id, commentRequestDto, userDetails.getUser());
    }

    @PutMapping("/api/comment/{id}/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long id, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, commentId, commentRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/api/comment/{id}/{commentId}")
    public ResponseEntity<StatusCodeDto> deleteComment(@PathVariable Long id, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, commentId, userDetails.getUser());
    }
}
