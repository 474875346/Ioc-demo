package com.m.mvc.exception;

public class SpringFrameworkLoadListenerException extends Exception {
    public SpringFrameworkLoadListenerException() {
        super();
    }

    public SpringFrameworkLoadListenerException(String message) {
        super(message);
    }

    public SpringFrameworkLoadListenerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpringFrameworkLoadListenerException(Throwable cause) {
        super(cause);
    }

    protected SpringFrameworkLoadListenerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
