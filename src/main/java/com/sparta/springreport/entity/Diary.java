package com.sparta.springreport.entity;

import com.sparta.springreport.dto.DiaryRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class Diary  extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

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
