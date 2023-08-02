package com.bjit.finalproject.TMS.exception.userExceptions;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String message){
        super(message);
    }
}
