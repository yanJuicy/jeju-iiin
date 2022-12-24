package com.example.jejuiiin.controller.response;

import com.example.jejuiiin.exception.ExceptionMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private final int httpStatusCode;
    private final String msg;
    private final T data;

    public Response(int httpStatusCode, String msg, T data){
        this.httpStatusCode = httpStatusCode;
        this.msg = msg;
        this.data = data;
    }

    public Response(ExceptionMessage exceptionMessage){
        this.httpStatusCode = exceptionMessage.getStatusCode();
        this.msg = exceptionMessage.getMsg();
        this.data = null;
    }
}
