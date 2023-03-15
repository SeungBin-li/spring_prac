package com.sparta.springreport.service;

import com.sparta.springreport.common.ApiException;
import com.sparta.springreport.common.ExceptionEnum;
import com.sparta.springreport.dto.CommentRequestDto;
import com.sparta.springreport.dto.CommentResponseDto;
import com.sparta.springreport.dto.MessageResponse;
import com.sparta.springreport.dto.StatusEnum;
import com.sparta.springreport.entity.Comment;
import com.sparta.springreport.entity.Diary;
import com.sparta.springreport.entity.User;
import com.sparta.springreport.entity.UserRoleEnum;
import com.sparta.springreport.jwt.JwtUtil;
import com.sparta.springreport.repository.CommentRepository;
import com.sparta.springreport.repository.DiaryRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final DiaryRepository diaryRepository;
    private final JwtUtil jwtUtil;
    private final CommentRepository commentRepository;


    public Comment getCommentAdminInfo(Long id, User user) {
        Comment comment;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            // 관리자 계정이기 때문에 게시글 아이디만 일치하면 수정,삭제 가능
            comment = commentRepository.findById(id).orElseThrow(
                    () -> new ApiException(ExceptionEnum.NOT_FOUND_COMMENT_ADMIN)
            );
        } else {
            // 사용자 계정이므로 게시글 아이디와 작성자 이름이 있는지 확인하고 있으면 수정,삭제 가능
            comment = commentRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new ApiException(ExceptionEnum.NOT_FOUND_COMMENT)
            );
        }
        return comment;
    }
    @Transactional
    public Diary getDiaryIdCheck(Long id) { // 게시글 id
        return diaryRepository.findById(id).orElseThrow(
                () -> new ApiException(ExceptionEnum.NOT_FOUND_POST_ALL)
        );
    }
    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, User user){
        Diary diary = getDiaryIdCheck(id);

        Comment comment = commentRepository.save(new Comment(diary, commentRequestDto, user));
        return new CommentResponseDto((comment));
    }
    @Transactional
    public CommentResponseDto updateComment(Long diaryid,
                                            Long commentid,
                                            CommentRequestDto requestDto,
                                            User user
                                            ){
        getDiaryIdCheck(diaryid);
        Comment comment = getCommentAdminInfo(commentid, user);
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public MessageResponse deletecomment(Long diaryid,
                                         Long commentid,
                                         User user){
        getDiaryIdCheck(diaryid);
        Comment comment = getCommentAdminInfo(commentid, user);
        commentRepository.deleteById(commentid);
        return  new MessageResponse(StatusEnum.OK);
    }
}
