package com.bjit.tss.exception;

import com.bjit.tss.mapper.ApiResponseMapper;
import com.bjit.tss.model.response.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> AllEx(Exception ex) {
        ExceptionModel<?> exceptionModel = ExceptionModel.builder()
                .error(ex.getMessage())
                .build();
        return ApiResponseMapper.mapToResponseEntityBadRequest(exceptionModel);
    }


    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ApiResponse<?>> DataIntegrirtyEx(Exception ex) {
        ExceptionModel<?> exceptionModel = ExceptionModel.builder()
                .error("Text size is too long")
                .build();
        return ApiResponseMapper.mapToResponseEntityUnauthorized(exceptionModel);
    }


    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ApiResponse<?>> AuthenticationEx(Exception ex) {
        ExceptionModel<?> exceptionModel = ExceptionModel.builder()
                .error(ex.getMessage())
                .build();
        return ApiResponseMapper.mapToResponseEntityUnauthorized(exceptionModel);
    }

    @ExceptionHandler({CourseException.class})
    public ResponseEntity<ApiResponse<?>> CourseEX(Exception ex) {
        ExceptionModel<?> exceptionModel = ExceptionModel.builder()
                .error(ex.getMessage())
                .build();
        return ApiResponseMapper.mapToResponseEntityBadRequest(exceptionModel);
    }

    @ExceptionHandler({FileUploadException.class})
    public ResponseEntity<ApiResponse<?>> FileUploadEX(Exception ex) {
        ExceptionModel<?> exceptionModel = ExceptionModel.builder()
                .error(ex.getMessage())
                .build();
        return ApiResponseMapper.mapToResponseEntityUnsupported(exceptionModel);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);

        });

        ApiResponse apiResponse = ApiResponse.builder()
                .errorMessage(errors)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
}