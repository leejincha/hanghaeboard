package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.*;
import com.sparta.hanghaeboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/api/posts")
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
    public DelResponseDto deleteMemo(@PathVariable Long id, HttpServletRequest request) {
        return postService.deletePost(id, request);
    }
}
