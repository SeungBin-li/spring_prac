package com.sparta.springreport.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException{
    private final ExceptionEnum errorCode;
}
