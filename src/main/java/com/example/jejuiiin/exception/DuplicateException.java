package com.example.jejuiiin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DuplicateException extends RuntimeException{
    protected final ExceptionMessage exceptionMessage;
}
