package com.sparta.springreport.entity;

import com.sparta.springreport.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name ="comments")
@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String comment;


    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(Diary diary, CommentRequestDto commentRequestDto, User user){
        this.diary = diary;
        this.user = user;
        this.username = user.getUsername();
        this.comment = commentRequestDto.getComment();
    }
    public void update(CommentRequestDto commentRequestDto){
        this.comment = commentRequestDto.getComment();
    }
}
