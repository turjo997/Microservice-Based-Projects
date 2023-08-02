package com.bjit.finalproject.TMS.exception.userExceptions;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message){
        super(message);
    }
}
