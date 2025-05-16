package com.itmang.exception;

public class InsufficientPermissionsException extends BaseException{
    public InsufficientPermissionsException() {
    }
    public InsufficientPermissionsException(String message) {
        super(message);
    }
}
