package com.spring.practise.exception;

public class ConfigParseException extends RuntimeException{
    public ConfigParseException(Exception exception){
        super(exception);
    }
}
