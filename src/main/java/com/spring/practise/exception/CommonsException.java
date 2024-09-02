package com.spring.practise.exception;

import lombok.Getter;

/**
 * This is a common runtime exception class for Exceptions in Commons Library
 * Accepts error code and error message
 */
@Getter
public class CommonsException extends RuntimeException {

    public CommonsException(String msg) {
        super(msg);
    }

    public CommonsException(String msg, Throwable e) {
        super(msg, e);
    }
}
