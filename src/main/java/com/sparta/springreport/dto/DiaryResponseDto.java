package com.sparta.springreport.dto;

import com.sparta.springreport.entity.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DiaryResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public DiaryResponseDto(Diary diary){
        this.id = diary.getId();
        this.username = diary.getUsername();
        this.contents = diary.getContents();
        this.title = diary.getTitle();
        this.createdAt = diary.getCreatedAt();
        this.modifiedAt = diary.getModifiedAt();
    }
}
