package com.SneakStyle.OrderService.util;

import com.SneakStyle.OrderService.exception.GeneralErrorException;
import com.SneakStyle.OrderService.exception.HttpErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.function.Supplier;

@Component
public class Helper {
    public static <T> ResponseEntity<T> executeRestCall(Supplier<ResponseEntity<T>> supplier) {
        try {
            return supplier.get();
        } catch (HttpClientErrorException ex) {
            throw new HttpErrorException("Failed to retrieve user details. HTTP error: " + ex.getRawStatusCode());
        } catch (Exception ex) {
            throw new GeneralErrorException("Failed to retrieve user details. Error: " + ex.getMessage());
        }
    }

}
