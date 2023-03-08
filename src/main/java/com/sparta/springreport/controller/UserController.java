package com.sparta.springreport.controller;

import com.sparta.springreport.dto.LoginRequestDto;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public ModelAndView signupPage(){
        return new ModelAndView("signup");
    }

    @GetMapping("/login")
    public ModelAndView loginPage(){
        return new ModelAndView("login");
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid SignupRequestDto signupRequestDto){
        return ResponseEntity.ok().body(userService.signup(signupRequestDto));
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return "success";
    }
}