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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String contents;

    public Diary(DiaryRequestDto diaryRequestDto){
        this.username = diaryRequestDto.getUsername();
        this.password = diaryRequestDto.getPassword();
        this.title = diaryRequestDto.getTitle();
        this.contents = diaryRequestDto.getContents();
    }

    public void update(DiaryRequestDto requestDto){
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
     }
}
