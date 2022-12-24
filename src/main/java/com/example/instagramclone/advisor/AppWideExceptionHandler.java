package com.example.instagramclone.advisor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@RestControllerAdvice
public class AppWideExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity
                .status(404)
                .body(e.getMessage());
    }
}
