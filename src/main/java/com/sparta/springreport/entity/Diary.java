package com.sparta.springreport.entity;

import com.sparta.springreport.dto.DiaryRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Diary  extends Timestamped{
    @Id
    @Column(name = "diary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    @OrderBy(value = "createdAt DESC")
    List<Comment> commentList = new ArrayList<>();

    public Diary(DiaryRequestDto diaryRequestDto, User user){
        this.username = user.getUsername();
        this.title = diaryRequestDto.getTitle();
        this.contents = diaryRequestDto.getContents();
        this.user = user;
    }

    public void update(DiaryRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
     }
}
