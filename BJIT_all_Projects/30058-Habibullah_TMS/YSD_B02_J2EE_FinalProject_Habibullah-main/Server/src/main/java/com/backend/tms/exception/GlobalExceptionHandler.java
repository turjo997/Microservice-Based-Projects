package com.backend.tms.exception;

import com.backend.tms.exception.custom.*;
import com.backend.tms.exception.custom.IllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            TraineeAlreadyExistsException.class,
            TrainerAlreadyExistException.class,
            BatchNotFoundException.class,
            BatchAlreadyExistsException.class,
            TraineeNotFoundException.class,
            TrainerNotFoundException.class,
            CourseNotFoundException.class,
            CourseAlreadyExistsException.class,
            ScheduleNotFoundException.class,
            InternalServerErrorException.class,
            AssignmentNotFoundException.class,
            PostNotFoundException.class,
            IllegalArgumentException.class,
            AdminAlreadyExistException.class,
            FileNotFoundException.class,
            CommentNotFoundException.class,
            NoticeNotFoundException.class,
            ClassroomNotFoundException.class,
            SubmittedAssignmentNotFound.class

    })
    public ResponseEntity<Object> handleCustomException(Exception ex) {
        if (ex instanceof TraineeAlreadyExistsException ) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } else if(ex instanceof BatchNotFoundException ){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }else if(ex instanceof TrainerAlreadyExistException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }else if (ex instanceof BatchAlreadyExistsException) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }else if (ex instanceof TrainerNotFoundException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }else if (ex instanceof TraineeNotFoundException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }else if (ex instanceof CourseNotFoundException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }else if (ex instanceof CourseAlreadyExistsException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (ex instanceof ScheduleNotFoundException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }else if (ex instanceof  AssignmentNotFoundException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }else if (ex instanceof  PostNotFoundException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }else if (ex instanceof  IllegalArgumentException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }else if (ex instanceof  FileNotFoundException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }else if (ex instanceof  NoticeNotFoundException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } else if (ex instanceof  ClassroomNotFoundException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } else if (ex instanceof  AdminAlreadyExistException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (ex instanceof  SubmittedAssignmentNotFound){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }  else{
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
