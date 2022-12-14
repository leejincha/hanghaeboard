package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.StatusCodeDto;
import com.sparta.hanghaeboard.entity.Comment;
import com.sparta.hanghaeboard.entity.CommentLike;
import com.sparta.hanghaeboard.entity.User;
import com.sparta.hanghaeboard.repository.CommentLikeRepository;
import com.sparta.hanghaeboard.repository.CommentRepository;
import com.sparta.hanghaeboard.util.ErrorCode;
import com.sparta.hanghaeboard.util.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    public StatusCodeDto commentLike(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.댓글이_존재하지_않습니다_400)
        );
        if(commentLikeRepository.findByUserIdAndCommentId(user.getId(),comment.getId()).isEmpty()){
            commentLikeRepository.save(new CommentLike(comment, user));
            return new StatusCodeDto(HttpStatus.OK.value(),"좋아요 성공");
        }else{
            commentLikeRepository.deleteByUserIdAndCommentId(user.getId(),comment.getId());
            return new StatusCodeDto(HttpStatus.OK.value(),"좋아요 취소");
        }
    }
}
