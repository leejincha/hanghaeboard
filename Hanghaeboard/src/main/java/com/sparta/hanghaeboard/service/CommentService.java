package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.CommentDto;
import com.sparta.hanghaeboard.entity.Comment;
import com.sparta.hanghaeboard.entity.Post;
import com.sparta.hanghaeboard.entity.User;
import com.sparta.hanghaeboard.entity.UserRoleEnum;
import com.sparta.hanghaeboard.jwt.JwtUtil;
import com.sparta.hanghaeboard.repository.CommentRepository;
import com.sparta.hanghaeboard.repository.PostRepository;
import com.sparta.hanghaeboard.repository.UserRepository;
import com.sparta.hanghaeboard.util.ErrorCode;
import com.sparta.hanghaeboard.util.RequestException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    public CommentDto addComment(CommentDto commentDto, Long id,  HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

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

            Post post = postRepository.findById(id).orElseThrow(
                    () -> new RequestException(ErrorCode.게시글이_존재하지_않습니다_400)
            );

            Comment comments = Comment.builder()
                    .post(post)
                    .username(user.getUsername())
                    .comment(commentDto.getComment())
                    .user(user)
                    .build();

            commentRepository.save(comments);

            return new CommentDto(comments);
        }else {
            /*return new ResponseMsgDto(HttpStatus.OK.value(), "실패");*/
            throw new RequestException(ErrorCode.유효하지_않은_토큰_400);
        }
    }

    @Transactional
    public CommentDto updateComment(Long id, Long commentId, CommentDto commentDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

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

            Comment comment;
            //유저의 권한이 admin과 같으면 모든 데이터 수정 가능
            if(user.getRole().equals(UserRoleEnum.ADMIN)){
                comment = commentRepository.findById(commentId).orElseThrow(NullPointerException::new);
            }else {
                comment = commentRepository.findByIdAndUserId(commentId, user.getId()).orElseThrow(
                        () -> new RequestException(ErrorCode.COMMON_BAD_REQUEST_400)
                );
            }
            comment.update(commentDto);

            return new CommentDto(comment);
        }else {
            throw new RequestException(ErrorCode.유효하지_않은_토큰_400);
        }
    }

    public void deleteComment(Long id, Long commentId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

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
            Comment comment;
            //유저의 권한이 admin과 같으면 모든 데이터 삭제 가능
            if (user.getRole().equals(UserRoleEnum.ADMIN)) {
                comment = commentRepository.findById(commentId).orElseThrow(NullPointerException::new);
            } else {
                comment = commentRepository.findByIdAndUserId(commentId, user.getId()).orElseThrow(
                        () -> new RequestException(ErrorCode.COMMON_BAD_REQUEST_400)
                );
            }

            commentRepository.delete(comment);
        }
    }
}

