package com.security.Auth.excption;

import com.security.Auth.student.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
//@ResponseStatus
public class RestExceptionhandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(ResponseStatusException.class)
        public ResponseEntity<ErrorMessage> globalExceptionHandler(ResponseStatusException ex, WebRequest request) {
                ErrorMessage err = new ErrorMessage(ex.getStatus(), ex.getMessage(), new Date());
                return ResponseEntity.status(ex.getStatus()).body(err);
        }
}
