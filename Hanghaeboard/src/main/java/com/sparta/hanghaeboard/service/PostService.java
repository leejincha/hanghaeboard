package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.CommentDto;
import com.sparta.hanghaeboard.dto.MsgResponseDto;
import com.sparta.hanghaeboard.dto.PostRequestDto;
import com.sparta.hanghaeboard.dto.PostResponseDto;
import com.sparta.hanghaeboard.entity.Comment;
import com.sparta.hanghaeboard.entity.Post;
import com.sparta.hanghaeboard.entity.User;
import com.sparta.hanghaeboard.entity.UserRoleEnum;
import com.sparta.hanghaeboard.repository.PostRepository;
import com.sparta.hanghaeboard.util.ErrorCode;
import com.sparta.hanghaeboard.util.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor //final에 붙은 친구들 생성자로 주입해줌 @Bean 주입
public class PostService {
    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = postRepository.save(new Post(requestDto, user));
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getListPosts() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postResponseDto = new ArrayList<>();
        for (Post post : postList) {
            List<CommentDto> commentList = new ArrayList<>();
            for (Comment comment : post.getCommentList()) {
                commentList.add(new CommentDto(comment));
            }
            PostResponseDto postDto = new PostResponseDto(post, commentList);
            postResponseDto.add(postDto);
        }
        return postResponseDto;
    }

    @Transactional
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.게시글이_존재하지_않습니다_400)
        );
        List<CommentDto> commentList = new ArrayList<>();
        for (Comment comment : post.getCommentList()) {
            commentList.add(new CommentDto(comment));
        }
        return new PostResponseDto(post, commentList);
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto requestDto, User user) {
        Post post;
        //유저의 권한이 admin과 같으면 모든 데이터 수정 가능
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            post = postRepository.findById(id).orElseThrow(
                    () -> new RequestException(ErrorCode.게시글이_존재하지_않습니다_400));
        } else {
            //유저의 권한이 admin이 아니면 아이디가 같은 유저만 수정 가능
            post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new RequestException(ErrorCode.작성자만_수정할_수_있습니다_400)
            );
        }
        post.update(requestDto);

        return new PostResponseDto(post);
    }

    @Transactional
    public ResponseEntity<MsgResponseDto> deletePost(Long id, User user) {
        Post post;
        //유저의 권한이 admin과 같으면 모든 데이터 삭제 가능
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            post = postRepository.findById(id).orElseThrow(
                    () -> new RequestException(ErrorCode.게시글이_존재하지_않습니다_400));
        } else {
            //유저의 권한이 admin이 아니면 아이디가 같은 유저만 삭제 가능
            post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new RequestException(ErrorCode.작성자만_삭제할_수_있습니다_400)
            );
        }
        postRepository.delete(post);

        return ResponseEntity.ok(new MsgResponseDto(HttpStatus.OK.value(), "게시글 삭제 성공"));
    }
}