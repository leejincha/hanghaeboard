package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.DelResponseDto;
import com.sparta.hanghaeboard.dto.PostRequestDto;
import com.sparta.hanghaeboard.dto.PostResponseDto;
import com.sparta.hanghaeboard.entity.Post;
import com.sparta.hanghaeboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    public PostResponseDto createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getListPosts() {
        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> postResponseDto = new ArrayList<>();
        for (Post post : postList) {
            PostResponseDto postDto = new PostResponseDto(post);
            postResponseDto.add(postDto);
        }
        return postResponseDto;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        PostResponseDto postResponseDto = new PostResponseDto(post);

        if (requestDto.getPassword().equals(post.getPassword())) {
            post.update(requestDto);
            return postResponseDto;
        } else {
            return postResponseDto;
        }

    }

    @Transactional
    public DelResponseDto deletePost(Long id, String password) { //매개값으로 id와 password만 사용 하였고 반환타입을 Dto로 바꿔주었다.
        Post post = postRepository.findById(id).orElseThrow( //이 부분은 업데이트와 동일하다.
                () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
        );

        DelResponseDto result = new DelResponseDto(); //DelResponseDto 를 생성해주고,
        if (post.getPassword().equals(password)) {     //비밀번호 일치여부를 조건문으로 확인한 뒤
            postRepository.deleteById(id);                  // 비밀번호가 일치하면 해당 아이디 게시글을 삭제해준다.
            result.setResult("success", "게시물이 삭제되었습니다."); //성공하면 다음과 같은 반환값을 준다.
            return result;
        } else {
            result.setResult("failed", "비밀번호가 일치하지 않습니다"); //비밀번호가 일치하지 않으면 다음과 같은 반환값을 준다.
            return result;
        }
    }
}