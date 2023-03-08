package com.sparta.springreport.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class SignupRequestDto {

    @NotBlank(message = "username을 입력해주세요")
    @Pattern(regexp = "^[a-z0-9]*$", message = "알파벳 소문자(a-z), 숫자(0~9)만 입력 가능")
    @Size(min = 4, max =10, message = "글자수 최소 4자, 최대 10이하")
    private String username;

    @NotBlank(message = "password를 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9]*$",message = "알파벳 대소문자(a-z,A-Z) 숫자(0~9)만 입력 가능")
    @Size(min = 8, max = 15, message = "최소 8자 이상, 최소 15자 이하의 숫자를 입력 가능")
    private String password;

    private boolean admin = false;
    private String adminToken ="";
}
