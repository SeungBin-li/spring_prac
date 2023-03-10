package com.sparta.springreport.controller;

import com.sparta.springreport.dto.DiaryListResponseDto;
import com.sparta.springreport.dto.DiaryRequestDto;
import com.sparta.springreport.dto.DiaryResponseDto;
import com.sparta.springreport.jwt.JwtUtil;
import com.sparta.springreport.repository.UserRepository;
import com.sparta.springreport.service.DiaryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @PostMapping("/api/diarys")
    public DiaryResponseDto writeDiary(@RequestBody DiaryRequestDto requestDto, HttpServletRequest request) {
        return diaryService.writeDiary(requestDto, request);
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
    public DiaryResponseDto updateDiary(@PathVariable Long id, @RequestBody DiaryRequestDto requestDto, HttpServletRequest request) {
        return diaryService.update(id, requestDto, request);
    }

    @DeleteMapping("/api/diarys/{id}")
    public ResponseEntity deleteDiary(@PathVariable Long id, HttpServletRequest request){
        return ResponseEntity.ok().body(diaryService.deleteDiary(id, request));
    }

}