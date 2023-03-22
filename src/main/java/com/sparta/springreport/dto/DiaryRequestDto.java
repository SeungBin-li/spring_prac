package com.sparta.springreport.dto;

import com.sparta.springreport.entity.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class DiaryRequestDto {
    private String title;
    private String contents;
}
