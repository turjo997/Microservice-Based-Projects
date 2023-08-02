package com.bjit.finalproject.TMS.exception;

import com.bjit.finalproject.TMS.exception.userExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<Object> passwordMisMatch(PasswordNotMatchException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> userException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({AlreadyExistsException.class, NameNotFoundException.class, CreationException.class, IsEmptyException.class})
    public ResponseEntity<Object> commonException(Exception ex){
        if(ex instanceof AlreadyExistsException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (ex instanceof NameNotFoundException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } else if(ex instanceof CreationException) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else if(ex instanceof IsEmptyException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ExceptionHandler(BatchDateException.class)
    public ResponseEntity<Object> batchDateExceptions(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ScheduleException.class})
    public ResponseEntity<Object> scheduleExceptions(Exception ex){
        if(ex instanceof ScheduleException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @ExceptionHandler(AssignmentDeadlineException.class)
    public ResponseEntity<Object> assignmentException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ClassroomException.class)
    public ResponseEntity<Object> classRoomExceptions(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
