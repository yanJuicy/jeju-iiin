package com.example.jejuiiin.controller.handler;

import com.example.jejuiiin.controller.exception.DuplicateException;
import com.example.jejuiiin.controller.exception.ExceptionMessage;
import com.example.jejuiiin.controller.exception.OAuth2LoginException;
import com.example.jejuiiin.controller.response.Response;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static com.example.jejuiiin.controller.exception.ExceptionMessage.INVALID_PAGE_NUMBER_MSG;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public Response<?> handle(NumberFormatException e) {
        return Response.fail(BAD_REQUEST.value(), INVALID_PAGE_NUMBER_MSG.getMsg());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(DuplicateException.class)
    public Response<?> handle(DuplicateException e) {
        ExceptionMessage exceptionMessage = e.getExceptionMessage();
        return Response.fail(exceptionMessage.getStatusCode(), exceptionMessage.getMsg());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Response<?> handle(NoSuchElementException e) {
        return Response.fail(BAD_REQUEST.value(), e.getMessage());
    }

	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ExceptionHandler(OAuth2LoginException.class)
	public Response<?> handle(OAuth2LoginException e) {
		return Response.fail(INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
}
