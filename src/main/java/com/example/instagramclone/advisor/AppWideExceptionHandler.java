package com.example.instagramclone.advisor;

import com.example.instagramclone.dto.APIResponse;
import com.example.instagramclone.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static com.example.instagramclone.util.ResponseConstants.FAILED;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@RestControllerAdvice
public class AppWideExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        List<ErrorDTO> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ErrorDTO errorDTO = new ErrorDTO(error.getField(), error.getDefaultMessage());
                    errors.add(errorDTO);
                });
        serviceResponse.setStatus(FAILED);
        serviceResponse.setErrors(errors);
        return serviceResponse;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity
                .status(404)
                .body(e.getMessage());
    }
}
