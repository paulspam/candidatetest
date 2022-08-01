package com.wamisoftware.candidatetest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchInputFileException.class)
    protected ResponseEntity<Object> handleNoSuchInputFileException(
        NoSuchInputFileException ex,
        WebRequest request) {

        log.info(ex.getClass().getName() + " handled");
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,
            ex.getLocalizedMessage(), ex.getLocalizedMessage());
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }
}
