package com.SneakStyle.ProductService.exception;

public class RecordNotFoundException extends RuntimeException
{
    public RecordNotFoundException(String message)
    {
        super(message);
    }
}