package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.MsgResponseDto;
import com.sparta.hanghaeboard.dto.PostRequestDto;
import com.sparta.hanghaeboard.dto.PostResponseDto;
import com.sparta.hanghaeboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService; //의존성 주입 ( 서비스다과 연결 )

    @PostMapping("/api/posts") //HttpServletRequest : Http프로토콜의 request 정보를 서블릿에게 전달하기 위한 목적으로 사용
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.createPost(requestDto, request);
    }

    @GetMapping("/api/posts")
    public List<PostResponseDto> getListPosts() {
        return postService.getListPosts();
    }

    @GetMapping("/api/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/api/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.update(id, requestDto, request);
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<MsgResponseDto> deleteMemo(@PathVariable Long id, HttpServletRequest request) {
        postService.deletePost(id, request);
        return ResponseEntity.ok(new MsgResponseDto("게시글 삭제 성공", HttpStatus.OK.value()));
    }
}
