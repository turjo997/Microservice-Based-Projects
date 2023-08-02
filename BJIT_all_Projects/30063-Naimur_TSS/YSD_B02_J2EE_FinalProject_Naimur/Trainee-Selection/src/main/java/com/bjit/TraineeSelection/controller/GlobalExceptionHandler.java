package com.bjit.TraineeSelection.controller;

import com.bjit.TraineeSelection.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationAlreadyExistsException.class)
    public ResponseEntity<String> handleApplicationAlreadyExistsException(ApplicationAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(CircularExist.class)
    public ResponseEntity<String>handleCircularExist(CircularExist ex)
    {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ApplicantAlreadyApprovedException.class)
    public ResponseEntity<String>handleApplicantAlreadyApproved(ApplicantAlreadyApprovedException ex)
    {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<Object> handleEmailAlreadyTakenException(EmailAlreadyTakenException ex) {
        // Create a custom response message

        // Return the custom response message in the response body with appropriate HTTP status
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EvaluatorAlreadyAssignedException.class)
    public  ResponseEntity<Object> handleEvaluatorAlreadyAssigned(EvaluatorAlreadyAssignedException ex)
    {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }


}