package com.backend.tms.exception.custom;

public class AssignmentNotFoundException extends RuntimeException{
    public AssignmentNotFoundException (String message){
        super (message);
    }

}
