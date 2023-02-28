package com.sparta.springreport.repository;

import com.sparta.springreport.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findAllByOrderByModifiedAtDesc();


    Optional<Diary> findFirstByIdAndPassword(Long id, String  password);

    void deleteByIdAndPassword(Long id, String password);
}