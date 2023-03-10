package com.sparta.springreport.controller;

import com.sparta.springreport.dto.CommentRequestDto;
import com.sparta.springreport.dto.CommentResponseDto;
import com.sparta.springreport.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/diary/{id}")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request){
        return commentService.createComment(id, requestDto, request);
    }

    @PutMapping("/diary/{diaryid}/comment/{commentid}")
    public CommentResponseDto modifyComment(
            @PathVariable Long diaryid,
            @PathVariable Long commentid,
            @RequestBody CommentRequestDto commentRequestDto,
            HttpServletRequest request){
        return commentService.updateComment(diaryid, commentid, commentRequestDto, request);
    }

    @DeleteMapping("/diary/{diaryid}/comment/{commentid}")
    public ResponseEntity deleteComment(@PathVariable Long diaryid,
                                        @PathVariable Long commentid,
                                        HttpServletRequest request){
        return ResponseEntity.ok().body(commentService.deletecomment(diaryid, commentid, request));
    }
}
