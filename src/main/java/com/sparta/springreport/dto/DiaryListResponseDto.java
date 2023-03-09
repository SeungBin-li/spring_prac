package com.sparta.springreport.dto;

import com.sparta.springreport.entity.Diary;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DiaryListResponseDto {
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public DiaryListResponseDto(Diary diary){
        this.username = diary.getUsername();
        this.title = diary.getTitle();
        this.contents = diary.getContents();
        this.createdAt = diary.getCreatedAt();
        this.modifiedAt = diary.getModifiedAt();
    }
}
