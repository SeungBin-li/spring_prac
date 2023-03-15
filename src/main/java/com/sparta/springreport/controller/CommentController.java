package com.sparta.springreport.controller;

import com.sparta.springreport.dto.CommentRequestDto;
import com.sparta.springreport.dto.CommentResponseDto;
import com.sparta.springreport.security.UserDetailsImpl;
import com.sparta.springreport.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{id}")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(id, requestDto, userDetails.getUser());
    }

    @PutMapping("/{diaryid}/comment/{commentid}")
    public CommentResponseDto modifyComment(
            @PathVariable Long diaryid,
            @PathVariable Long commentid,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(diaryid, commentid, commentRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/{diaryid}/comment/{commentid}")
    public ResponseEntity deleteComment(@PathVariable Long diaryid,
                                        @PathVariable Long commentid,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok().body(commentService.deletecomment(diaryid, commentid, userDetails.getUser()));
    }
}
