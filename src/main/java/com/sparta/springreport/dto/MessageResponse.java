package com.sparta.springreport.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
    private int status;
    private String message;

    public MessageResponse(StatusEnum statusEnum){
        this.status = statusEnum.statusCode;
        this.message = statusEnum.msg;
    }
}
