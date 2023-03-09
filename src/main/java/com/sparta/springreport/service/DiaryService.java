package com.sparta.springreport.service;

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

    @Transactional
    public DiaryResponseDto writeDiary(DiaryRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Diary diary = diaryRepository.saveAndFlush(new Diary(requestDto, user));
            return new DiaryResponseDto(diary);
        } else {
            return null;
        }
    }
    public DiaryListResponseDto getDiarys(Long id){
        Diary diary = diaryRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("해당 게시물 없음")
        );
        return new DiaryListResponseDto(diary);
    }

    @Transactional(readOnly = true)
    public List<DiaryListResponseDto> getDiarys() {
        List<DiaryListResponseDto> diaryResponseDtoList = new ArrayList<>();

        List<Diary> diaryList = diaryRepository.findAllByOrderByCreatedAtDesc();
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        for (Diary diary : diaryList) {
            diaryResponseDtoList.add(new DiaryListResponseDto(diary));
            log.info("post = {}", diary);
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
    public DiaryResponseDto update(Long id, DiaryRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Diary diary = diaryRepository.findByIdAndUsername(id, claims.getSubject()).orElseThrow(
                    () -> new NullPointerException("해당 게시물은 존재하지 않습니다.")
            );
            diary.update(requestDto);
            return new DiaryResponseDto(diary);
        } else {
            return null;
        }
    }

    @Transactional
    public MessageResponse deleteDiary(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    ()-> new IllegalArgumentException("사용자가 존재하지 않습니다")
            );
            Diary diary = diaryRepository.findByIdAndUsername(id, claims.getSubject()).orElseThrow(
                    ()->new NullPointerException("해당 게시글이 존재하지 않습니다")
            );

            diaryRepository.deleteById(id);
            return new MessageResponse(StatusEnum.OK);

        }
        throw  new IllegalArgumentException("not followed access(no token)");
    }
}