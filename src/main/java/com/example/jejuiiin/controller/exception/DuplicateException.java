package com.example.jejuiiin.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class DuplicateException extends RuntimeException{
    private final ExceptionMessage exceptionMessage;
}
