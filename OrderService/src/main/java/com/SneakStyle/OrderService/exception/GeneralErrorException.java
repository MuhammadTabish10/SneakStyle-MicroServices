package com.SneakStyle.OrderService.exception;

public class GeneralErrorException extends RuntimeException {
    public GeneralErrorException(String message) {
        super(message);
    }
}