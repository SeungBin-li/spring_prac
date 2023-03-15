package com.sparta.springreport.service;

import com.sparta.springreport.common.ApiException;
import com.sparta.springreport.common.ExceptionEnum;
import com.sparta.springreport.dto.*;
import com.sparta.springreport.entity.Diary;
import com.sparta.springreport.entity.User;
import com.sparta.springreport.entity.UserRoleEnum;
import com.sparta.springreport.jwt.JwtUtil;
import com.sparta.springreport.repository.DiaryRepository;
import com.sparta.springreport.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    //게시글 저장
    @Transactional
    public DiaryResponseDto writeDiary(DiaryRequestDto requestDto, User user) {
            Diary diary = diaryRepository.saveAndFlush(new Diary(requestDto, user));
            return new DiaryResponseDto(diary);
    }

    //개인 게시글 조회
    @Transactional
    public DiaryListResponseDto getDiarys(Long id){
        Diary diary = diaryRepository.findById(id).orElseThrow(
                ()-> new ApiException(ExceptionEnum.NOT_FOUND_POST_ALL)
        );
        return new DiaryListResponseDto(diary);
    }

    //게시글 전체 조회
    @Transactional(readOnly = true)
    public List<DiaryListResponseDto> getDiarys() {

        List<DiaryListResponseDto> diaryResponseDtoList = new ArrayList<>();

        List<Diary> diaryList = diaryRepository.findAllByOrderByCreatedAtDesc();

        for (Diary diary : diaryList) {
            diaryResponseDtoList.add(new DiaryListResponseDto(diary));

        }
            // 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
            /*UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            List<Diary> diaryList;

            if (userRoleEnum == UserRoleEnum.USER) {
                // 사용자 권한이 USER일 경우
                diaryList = diaryRepository.findAll(user.getId());
            } else {
                diaryList = diaryRepository.findAll();
            }
            return diaryList;

        } else {
            return null;
        }*/
        return diaryResponseDtoList;
    }

    @Transactional
    public DiaryResponseDto update(Long id,
                                       DiaryRequestDto diaryRequestDto,
                                      User user) {

        Diary diary = getPostAdminInfo(id, user);
        diary.update(diaryRequestDto); // 이미 존재하는 post객체를 수정하고 업데이트하는 데 사용한다.
        return new DiaryResponseDto(diary);
    }

    @Transactional
    public MessageResponse deleteDiary(Long id, User user) {
        getPostAdminInfo(id, user);
        diaryRepository.deleteById(id);
        return new MessageResponse(StatusEnum.OK);
    }

    public Diary getPostAdminInfo(Long id, User user) {
        Diary diary;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            // 관리자 계정이기 때문에 게시글 아이디만 일치하면 수정,삭제 가능
            diary = diaryRepository.findById(id).orElseThrow(
                    () -> new ApiException(ExceptionEnum.NOT_FOUND_POST_ADMIN)
            );
        } else {
            // 사용자 계정이므로 게시글 아이디와 작성자 이름이 있는지 확인하고 있으면 수정,삭제 가능
            diary = diaryRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new ApiException(ExceptionEnum.NOT_FOUND_POST)
            );
        }
        return diary;
    }
}