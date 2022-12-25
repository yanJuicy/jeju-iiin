package com.example.jejuiiin.security.jwt.exception;

import com.example.jejuiiin.exception.ExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomSecurityException extends RuntimeException{
    private final ExceptionMessage exceptionMessage;
}
