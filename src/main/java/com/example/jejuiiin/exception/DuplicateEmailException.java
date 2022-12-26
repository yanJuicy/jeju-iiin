package com.example.jejuiiin.exception;

import lombok.Getter;

@Getter
public class DuplicateEmailException extends DuplicateException{
    public DuplicateEmailException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }
}
