package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<CommonResponse<?>> responseStatusExceptionHandler(ResponseStatusException ex) {
        CommonResponse<?> response = CommonResponse.builder()
                .statusCode(Integer.valueOf(Objects.requireNonNull(ex.getReason())))
                .build();

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(response);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<CommonResponse<?>> constraintViolationExceptionHandler(DataIntegrityViolationException ex) {
        CommonResponse<?> response = CommonResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<CommonResponse<?>> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex) {
        CommonResponse.CommonResponseBuilder<Object> builder = CommonResponse.builder();
        HttpStatus httpStatus;

        if (ex.getMessage().contains("ForeignKey Constraint")) {
            builder.statusCode(HttpStatus.BAD_REQUEST.value());
            builder.message(ConstantMessage.BAD_REQUEST);
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (ex.getMessage().contains("UniqueKey Constraint") || ex.getMessage().contains("Duplicated Entry")) {
            builder.statusCode(HttpStatus.CONFLICT.value());
            builder.message(ConstantMessage.CONFLICT);
            httpStatus = HttpStatus.CONFLICT;
        } else {
            builder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            builder.message(ConstantMessage.INTERNAL_SERVER_ERROR);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity
                .status(httpStatus)
                .body(builder.build());
    }

}