package com.sparta.springreport.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionController {
    @ExceptionHandler(value ={ApiException.class})
    protected ResponseEntity<ApiExceptionResponse> handelCustomException(ApiException e){
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ApiExceptionResponse.toResponseEntity(e.getErrorCode());
    }
}
