package com.example.jejuiiin.exception;

import lombok.Getter;

@Getter
public class DuplicateLoginIdException extends DuplicateException {
    public DuplicateLoginIdException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }
}
