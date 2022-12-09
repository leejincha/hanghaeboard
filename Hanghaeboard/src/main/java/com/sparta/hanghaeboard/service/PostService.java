package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.MsgResponseDto;
import com.sparta.hanghaeboard.dto.PostRequestDto;
import com.sparta.hanghaeboard.dto.PostResponseDto;
import com.sparta.hanghaeboard.entity.Post;
import com.sparta.hanghaeboard.entity.User;
import com.sparta.hanghaeboard.entity.UserRoleEnum;
import com.sparta.hanghaeboard.jwt.JwtUtil;
import com.sparta.hanghaeboard.repository.PostRepository;
import com.sparta.hanghaeboard.repository.UserRepository;
import com.sparta.hanghaeboard.util.ErrorCode;
import com.sparta.hanghaeboard.util.RequestException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor //final에 붙은 친구들 생성자로 주입해줌 @Bean 주입
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new RequestException(ErrorCode.유효하지_않은_토큰_400);
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new RequestException(ErrorCode.사용자가_존재하지_않습니다_400)
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Post post = postRepository.saveAndFlush(new Post(requestDto, user.getUsername(), user));
            return new PostResponseDto(post);
        } else {
            throw new RequestException(ErrorCode.유효하지_않은_토큰_400);
        }
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getListPosts() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postResponseDto = new ArrayList<>();
        for (Post post : postList) {
                PostResponseDto postDto = new PostResponseDto(post);
                postResponseDto.add(postDto);
        }
        return postResponseDto;
    }

    @Transactional
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 업데이트 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new RequestException(ErrorCode.유효하지_않은_토큰_400);
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new RequestException(ErrorCode.사용자가_존재하지_않습니다_400)
            );

            Post post;
            //유저의 권한이 admin과 같으면 모든 데이터 수정 가능
            if (user.getRole().equals(UserRoleEnum.ADMIN)) {
                post = postRepository.findById(id).orElseThrow(NullPointerException::new);
            } else {
                //유저의 권한이 admin이 아니면 아이디가 같은 유저만 수정 가능
                post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new RequestException(ErrorCode.아이디가_일치하지_않습니다)
                );
            }
            post.update(requestDto);

            return new PostResponseDto(post);
        } else {
            throw new RequestException(ErrorCode.유효하지_않은_토큰_400);
        }
    }

    //postRepository.deleteById(id);
    @Transactional
    public ResponseEntity<MsgResponseDto> deletePost(Long id, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 삭제 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new RequestException(ErrorCode.유효하지_않은_토큰_400);
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new RequestException(ErrorCode.사용자가_존재하지_않습니다_400)
            );

            Post post;
            //유저의 권한이 admin과 같으면 모든 데이터 삭제 가능
            if (user.getRole().equals(UserRoleEnum.ADMIN)) {
                post = postRepository.findById(id).orElseThrow(NullPointerException::new);
            } else {
                //유저의 권한이 admin이 아니면 아이디가 같은 유저만 삭제 가능
                post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new RequestException(ErrorCode.아이디가_일치하지_않습니다)
                );
            }

            postRepository.delete(post);

            return ResponseEntity.ok(new MsgResponseDto(HttpStatus.OK.value(),"게시글 삭제 성공"));
        }else{
            throw new RequestException(ErrorCode.유효하지_않은_토큰_400);
        }
    }
}