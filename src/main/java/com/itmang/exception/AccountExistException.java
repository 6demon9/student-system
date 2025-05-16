package com.itmang.exception;

public class AccountExistException extends BaseException{
    public AccountExistException() {
    }
    public AccountExistException(String message) {
        super(message);
    }
}
