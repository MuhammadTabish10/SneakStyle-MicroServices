package com.SneakStyle.UserService.exception;

public class UserAlreadyExistAuthenticationException extends RuntimeException{
    public UserAlreadyExistAuthenticationException(String message)
    {
        super(message);
    }
}
