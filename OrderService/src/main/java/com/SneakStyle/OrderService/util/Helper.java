package com.SneakStyle.OrderService.util;

import com.SneakStyle.OrderService.exception.GeneralErrorException;
import com.SneakStyle.OrderService.exception.HttpErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import com.SneakStyle.OrderService.dto.constants.Messages;

import java.util.function.Supplier;

@Component
public class Helper {
    public static <T> ResponseEntity<T> executeRestCall(Supplier<ResponseEntity<T>> supplier) {
        try {
            return supplier.get();
        } catch (HttpClientErrorException ex) {
            throw new HttpErrorException(String.format(Messages.FAILED_RETRIEVE_USER_DETAILS_HTTP, ex.getRawStatusCode()));
        } catch (Exception ex) {
            throw new GeneralErrorException(String.format(Messages.FAILED_RETRIEVE_USER_DETAILS_ERROR, ex.getMessage()));
        }
    }

}
