package com.sparta.hanghaeboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.hanghaeboard.dto.CommentRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String comment;

    @JsonIgnore//순환 참조 = 무한 루프, 제이슨으로 파싱하는 것을 무시하고 FK만 들고오도록 함.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @Builder
    public Comment(CommentRequestDto commentRequestDto, Post post, User user) {
        this.comment = commentRequestDto.getComment();
        this.username = user.getUsername();
        this.post = post;
        this.user = user;
    }


    public void update(CommentRequestDto commentRequestDto){
        this.comment = commentRequestDto.getComment();
    }
}
