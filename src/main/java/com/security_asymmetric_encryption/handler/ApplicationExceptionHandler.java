package com.security_asymmetric_encryption.handler;
import com.security_asymmetric_encryption.enums.ErrorCode;
import com.security_asymmetric_encryption.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.function.array.ArrayViaElementArgumentReturnTypeResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static com.security_asymmetric_encryption.enums.ErrorCode.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApplicationExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse>  handleException(final BusinessException ex) {

        final ErrorResponse body = ErrorResponse.builder()
                .message(ex.getMessage())
                .code(ex.getErrorCode().getCode())
                .build();
        log.info("BusinessException : {}",ex.getMessage());
        log.debug(ex.getMessage(),ex);
        return ResponseEntity.status(ex.getErrorCode()
                .getStatus() != null ? ex.getErrorCode().getStatus() : BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse>  handleException(final DisabledException ex) {
        final ErrorResponse body = ErrorResponse.builder()
                .code(ACCOUNT_USER_DISABLED.getCode())
                .message(ACCOUNT_USER_DISABLED.getDefaultMessage())
                .build();
        return ResponseEntity.status(ACCOUNT_USER_DISABLED.getStatus())
                .body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse>  handleException(final BadCredentialsException ex) {
        log.debug(ex.getMessage(),ex);
        final ErrorResponse response = ErrorResponse.builder()
                .code(BAD_CREDENTIALS.getCode())
                .message(BAD_CREDENTIALS.getDefaultMessage())
                .build();
        return ResponseEntity.status(BAD_CREDENTIALS.getStatus())
                .body(response);

    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse>  handleException(final UsernameNotFoundException ex) {
        log.debug(ex.getMessage(),ex);
        final ErrorResponse response = ErrorResponse.builder()
                .code(USERNAME_NOT_FOUND.getCode())
                .message(USERNAME_NOT_FOUND.getDefaultMessage())
                .build();
        return new ResponseEntity<>(response, NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse>  handleException(final EntityNotFoundException ex) {
        log.debug(ex.getMessage(),ex);
        final ErrorResponse response = ErrorResponse.builder()
                .code("TBD")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>  handleException(final MethodArgumentNotValidException ex) {
        final List<ErrorResponse.ValidationErrors> errors = new ArrayList<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    final String fieldName = ((FieldError) error).getField();
                    final String errorCode = error.getDefaultMessage();
                    errors.add(ErrorResponse.ValidationErrors.builder()
                                    .field(fieldName)
                                    .code(errorCode)
                                    .message(errorCode)
                            .build());
                });
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .validationErrors(errors)
                .build();
        return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>  handleException(final Exception ex) {
        log.error(ex.getMessage(),ex);
        final ErrorResponse response = ErrorResponse.builder()
                .code(INTERNAL_EXCEPTION.getCode())
                .message(INTERNAL_EXCEPTION.getDefaultMessage())
                .build();
        return ResponseEntity.status(INTERNAL_EXCEPTION.getStatus())
                .body(response);

    }






}
