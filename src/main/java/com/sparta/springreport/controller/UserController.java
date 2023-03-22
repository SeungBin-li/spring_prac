package com.sparta.springreport.controller;

import com.sparta.springreport.dto.LoginRequestDto;
import com.sparta.springreport.dto.MessageResponse;
import com.sparta.springreport.dto.SignupRequestDto;
import com.sparta.springreport.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")//
    public ResponseEntity signup(@RequestBody @Valid SignupRequestDto signupRequestDto){
        return ResponseEntity.ok().body(userService.signup(signupRequestDto));
    }

    //@ResponseBody
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok().body(userService.login(loginRequestDto, response));
    }
}
