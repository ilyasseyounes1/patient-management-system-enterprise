package org.ilyasse.patientservice.exception;

public class EmailAlreadyExistsException extends  RuntimeException{
    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
