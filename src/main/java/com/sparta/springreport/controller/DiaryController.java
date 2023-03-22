package com.sparta.springreport.controller;

import com.sparta.springreport.dto.DiaryListResponseDto;
import com.sparta.springreport.dto.DiaryRequestDto;
import com.sparta.springreport.dto.DiaryResponseDto;
import com.sparta.springreport.jwt.JwtUtil;
import com.sparta.springreport.repository.UserRepository;
import com.sparta.springreport.security.UserDetailsImpl;
import com.sparta.springreport.service.DiaryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    @PostMapping("/api/diarys")
    public DiaryResponseDto writeDiary(@RequestBody DiaryRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return diaryService.writeDiary(requestDto, userDetails.getUser());
    }


    @GetMapping("/api/diarys")
    public List<DiaryListResponseDto> getDiarys(){
        return diaryService.getDiarys();
    }

    @GetMapping("/api/diarys/{id}")
    public DiaryListResponseDto getDiarys(@PathVariable Long id){
        return diaryService.getDiarys(id);
    }

    @PutMapping("/api/diarys/{id}")
    public DiaryResponseDto updateDiary(@PathVariable Long id, @RequestBody DiaryRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return diaryService.update(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/api/diarys/{id}")
    public ResponseEntity deleteDiary(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok().body(diaryService.deleteDiary(id, userDetails.getUser()));
    }

}