package com.ifkusyoba.itam_harkan_pal.core.exception;

import com.ifkusyoba.itam_harkan_pal.core.WebResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidPasswordException.class)
    public WebResponse<Object> handleInvalidPasswordException(Exception e) {
        return WebResponse.<Object>builder()
                .message("An error occurred: " + e.getMessage())
                .data(null)
                .isSuccess(false)
                .build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public WebResponse<Object> handleUserNotFoundException(Exception e) {
        return WebResponse.<Object>builder()
                .message("An error occurred: " + e.getMessage())
                .data(null)
                .isSuccess(false)
                .build();
    }

    @ExceptionHandler(DataNotFoundException.class)
    public WebResponse<Object> handleDataNotFoundException(Exception e) {
        return WebResponse.<Object>builder()
                .message("An error occurred: " + e.getMessage())
                .data(null)
                .isSuccess(false)
                .build();
    }
}
