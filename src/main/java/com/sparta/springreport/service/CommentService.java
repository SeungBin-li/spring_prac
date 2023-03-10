package com.sparta.springreport.service;

import com.sparta.springreport.dto.CommentRequestDto;
import com.sparta.springreport.dto.CommentResponseDto;
import com.sparta.springreport.dto.MessageResponse;
import com.sparta.springreport.dto.StatusEnum;
import com.sparta.springreport.entity.Comment;
import com.sparta.springreport.entity.Diary;
import com.sparta.springreport.entity.User;
import com.sparta.springreport.jwt.JwtUtil;
import com.sparta.springreport.repository.CommentRepository;
import com.sparta.springreport.repository.DiaryRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final DiaryRepository diaryRepository;
    private final JwtUtil jwtUtil;
    private final CommentRepository commentRepository;

    public Diary getDiaryIdCheck(Long id) { // 게시글 id
        return diaryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id 불일치")
        );
    }

    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request){
        Diary diary = getDiaryIdCheck(id);

        User user = jwtUtil.getUserInfo(request);

        Comment comment = commentRepository.save(new Comment(diary, commentRequestDto, user));
        return new CommentResponseDto((comment));
    }

    public CommentResponseDto updateComment(Long diaryid,
                                            Long commentid,
                                            CommentRequestDto requestDto,
                                            HttpServletRequest request
                                            ){
        Diary diary = getDiaryIdCheck(diaryid);
        User user = jwtUtil.getUserInfo(request);
        Comment comment = jwtUtil.getCommentAdminInfo(commentid, user);
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    public MessageResponse deletecomment(Long diaryid,
                                         Long commentid,
                                         HttpServletRequest request){
        Diary diary = getDiaryIdCheck(diaryid);
        User user = jwtUtil.getUserInfo(request);
        Comment comment = jwtUtil.getCommentAdminInfo(commentid, user);
        commentRepository.deleteById(commentid);
        return  new MessageResponse(StatusEnum.OK);
    }
}
