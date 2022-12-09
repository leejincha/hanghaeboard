package com.sparta.hanghaeboard.dto;

import com.sparta.hanghaeboard.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentDto {
    private Long commentId;
    private Long postId;
    private String username;
    private String comment;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;


    public CommentDto(Comment comment) {
        this.commentId = comment.getId();
        this.postId = comment.getPost().getId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt();
        this. modifiedAt = comment.getModifiedAt();
    }
}
