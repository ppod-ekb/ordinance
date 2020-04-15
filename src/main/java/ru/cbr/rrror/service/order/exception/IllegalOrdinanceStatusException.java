package ru.cbr.rrror.service.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class IllegalOrdinanceStatusException extends RuntimeException {
    public IllegalOrdinanceStatusException(String message) {
        super(message);
    }
}
