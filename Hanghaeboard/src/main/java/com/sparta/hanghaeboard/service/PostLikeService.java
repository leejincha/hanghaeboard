package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.MsgResponseDto;
import com.sparta.hanghaeboard.entity.Post;
import com.sparta.hanghaeboard.entity.PostLike;
import com.sparta.hanghaeboard.entity.User;
import com.sparta.hanghaeboard.repository.PostLikeRepository;
import com.sparta.hanghaeboard.repository.PostRepository;
import com.sparta.hanghaeboard.util.ErrorCode;
import com.sparta.hanghaeboard.util.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    public MsgResponseDto postLike(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.게시글이_존재하지_않습니다_400)
        );
        if(postLikeRepository.findByUserIdAndPostId(user.getId(), post.getId()).isEmpty()){
            postLikeRepository.save(new PostLike(post, user));
            return new MsgResponseDto(HttpStatus.OK.value(),"좋아요 성공");
        }else{
            postLikeRepository.deleteByUserIdAndPostId(user.getId(),post.getId());
            return new MsgResponseDto(HttpStatus.OK.value(),"좋아요 취소");
        }
    }
}
