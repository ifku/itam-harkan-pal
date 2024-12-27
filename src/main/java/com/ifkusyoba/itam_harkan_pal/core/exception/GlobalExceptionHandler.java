package com.ifkusyoba.itam_harkan_pal.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ifkusyoba.itam_harkan_pal.core.WebResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(OutOfTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public WebResponse<String> handleOutOfTimeException(OutOfTimeException e) {
        return WebResponse.<String>builder()
                .message(e.getMessage())
                .data(null)
                .isSuccess(false)
                .build();
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public WebResponse<String> handleDataNotFoundException(DataNotFoundException e) {
        return WebResponse.<String>builder()
                .message(e.getMessage())
                .data(null)
                .isSuccess(false)
                .build();
    }

    @ExceptionHandler(DuplicateDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public WebResponse<String> handleDuplicateDataException(DuplicateDataException e) {
        return WebResponse.<String>builder()
                .message(e.getMessage())
                .data(null)
                .isSuccess(false)
                .build();
    }

    @ExceptionHandler(TimeLimitExceededException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public WebResponse<String> handleTimeLimitExceededException(TimeLimitExceededException e) {
        return WebResponse.<String>builder()
                .message(e.getMessage())
                .data(null)
                .isSuccess(false)
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public WebResponse<String> handleGenericException(Exception e) {
        return WebResponse.<String>builder()
                .message("An unexpected error occurred: " + e.getMessage())
                .data(null)
                .isSuccess(false)
                .build();
    }
}