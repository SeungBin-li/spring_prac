package com.sparta.springreport.service;

import com.sparta.springreport.dto.DiaryDeleteDto;
import com.sparta.springreport.dto.DiaryRequestDto;
import com.sparta.springreport.entity.Diary;
import com.sparta.springreport.entity.Timestamped;
import com.sparta.springreport.repository.DiaryRepository;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    @Transactional
    public Diary writeDiary(DiaryRequestDto requestDto) {
        Diary diary = new Diary(requestDto);
        diaryRepository.save(diary);
        return diary;
    }

    public List<Diary> getDiarys() {
        return diaryRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public Long update(Long id, DiaryRequestDto requestDto) {
        Diary diary = diaryRepository.findFirstByIdAndPassword(id, requestDto.getPassword()).orElseThrow(
                () -> new IllegalArgumentException("아이디가 없거나 비밀번호 틀림")
        );
        diary.update(requestDto);
        return diary.getId();
    }

    @Transactional
    public Long deleteDiary(Long id, DiaryDeleteDto deleteDto) {
        diaryRepository.deleteByIdAndPassword(id, deleteDto.getPassword());
        return 0L;
    }
}