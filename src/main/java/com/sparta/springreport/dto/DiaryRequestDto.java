package com.sparta.springreport.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DiaryRequestDto {
    private String username;
    private String password;
    private String title;
    private String contents;
}
