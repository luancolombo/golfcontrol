package com.golf.control.exception;

public class GolfException extends RuntimeException{
    public GolfException(Object id) {
        super("Exception: " +id);
    }
}
