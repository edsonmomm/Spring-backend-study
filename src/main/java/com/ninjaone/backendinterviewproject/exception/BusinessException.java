package com.ninjaone.backendinterviewproject.exception;

/**
 * Standard exception to be handled by Resourse Exception Handler
 * Other exceptions will extend this one, so the handler will be able to catch one and treat properly
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }
}
