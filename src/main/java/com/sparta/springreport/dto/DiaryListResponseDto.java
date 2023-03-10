package com.sparta.springreport.dto;

import com.sparta.springreport.entity.Comment;
import com.sparta.springreport.entity.Diary;
import com.sparta.springreport.service.CommentService;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class DiaryListResponseDto {
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    public DiaryListResponseDto(Diary diary){
        this.username = diary.getUsername();
        this.title = diary.getTitle();
        this.contents = diary.getContents();
        this.createdAt = diary.getCreatedAt();
        this.modifiedAt = diary.getModifiedAt();
        for(Comment comment : diary.getCommentList()){
            commentList.add(new CommentResponseDto(comment));
        }
    }
}
