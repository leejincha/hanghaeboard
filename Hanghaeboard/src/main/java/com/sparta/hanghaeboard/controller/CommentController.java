package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.CommentDto;
import com.sparta.hanghaeboard.dto.MsgResponseDto;
import com.sparta.hanghaeboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/api/comment/{id}")
    public CommentDto addComment(@PathVariable Long id, @RequestBody CommentDto commentDto, HttpServletRequest request) {
        // 응답 보내기
        return commentService.addComment(commentDto, id, request);
    }

    @PutMapping("/api/comment/{id}/{commentId}")
    public CommentDto updateComment(@PathVariable Long id,@PathVariable Long commentId, @RequestBody CommentDto commentDto, HttpServletRequest request) {
        return commentService.updateComment(id, commentId, commentDto, request);
    }

    @DeleteMapping("/api/comment/{id}/{commentId}")
    public MsgResponseDto deleteComment(@PathVariable Long id, @PathVariable Long commentId, HttpServletRequest request) {
        commentService.deleteComment(id, commentId, request);
        return new MsgResponseDto(HttpStatus.OK.value(), "댓글 삭제 성공");
    }
}
