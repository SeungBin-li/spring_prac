package com.sparta.springreport.dto;

import com.sparta.springreport.entity.Comment;
import com.sparta.springreport.entity.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DiaryResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    public DiaryResponseDto(Diary diary){
        this.id = diary.getId();
        this.username = diary.getUsername();
        this.contents = diary.getContents();
        this.title = diary.getTitle();
        this.createdAt = diary.getCreatedAt();
        this.modifiedAt = diary.getModifiedAt();
        for(Comment comment : diary.getCommentList()){
            commentList.add(new CommentResponseDto(comment));
        }
    }
}
