package com.sparta.hanghaeboard.entity;

import com.sparta.hanghaeboard.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter//Class 내 모든 필드의 Getter method를 자동 생성한다.
@Entity//실제 DB의 테이블과 매칭될 Class임을 명시한다.
@NoArgsConstructor//Entity Class를 프로젝트 코드상에서 기본생성자로 생성하는 것은 금지하고, JPA에서 Entity 클래스를 생성하는것은 허용하기 위해 추가한다.
public class Post extends Timestamped {
    @Id //해당 테이블의 PK 필드를 나타낸다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK의 생성 규칙을 나타낸다.
    private Long id;
    @Column(nullable = false) //테이블의 컬럼을 나타내며, 굳이 선언하지 않더라도 해당 Class의 필드는 모두 컬럼이 된다.
    private String username;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String title;

    public Post(PostRequestDto requestDto, String username) {
        this.username = username; //게시글 작성에 필요한 username
        this.content = requestDto.getContent(); //내용
        this.title = requestDto.getTitle(); //제목
    }

    public void update(PostRequestDto postRequestDto) { //업데이트 부분이기 때문에 두 항목만 추가
        this.content = postRequestDto.getContent(); //수정에 필요한 내용
        this.title = postRequestDto.getTitle(); //제목
    }
}