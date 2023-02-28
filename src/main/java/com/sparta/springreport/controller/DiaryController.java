package com.sparta.springreport.controller;

import com.sparta.springreport.dto.DiaryDeleteDto;
import com.sparta.springreport.dto.DiaryRequestDto;
import com.sparta.springreport.entity.Diary;
import com.sparta.springreport.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @PostMapping("/api/diarys")
    public Diary writeDiary(@RequestBody DiaryRequestDto requestDto) {
        return diaryService.writeDiary(requestDto);
    }

    @GetMapping("/api/diarys")
    public List<Diary> getDiarys() {
        return diaryService.getDiarys();
    }

    @PutMapping("/api/diarys/{id}")
    public ResponseEntity updateDiary(@PathVariable Long id, @RequestBody DiaryRequestDto requestDto) {
        diaryService.update(id, requestDto);
        return ResponseEntity.ok(id);
    }
    @DeleteMapping("/api/diarys/{id}")
    public Long deleteMemo(@PathVariable Long id, @RequestBody DiaryDeleteDto deleteDto){
        return diaryService.deleteDiary(id, deleteDto);
    }
}