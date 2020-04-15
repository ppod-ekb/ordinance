package ru.cbr.rrror.service.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.cbr.rrror.service.order.entity.OrdinanceStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrdinanceStatusNotPresentException extends RuntimeException {

    public OrdinanceStatusNotPresentException(OrdinanceStatus.Code code) {
        super("Ordinance status not found by code: " + code.toString());
    }

    public OrdinanceStatusNotPresentException() {
        super("Ordinance status not found");
    }
}
