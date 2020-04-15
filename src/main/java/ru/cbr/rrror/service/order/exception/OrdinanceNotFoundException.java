package ru.cbr.rrror.service.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrdinanceNotFoundException extends RuntimeException {

    public OrdinanceNotFoundException(Long id) {
        super("could't found Ordinance by id: " + id);
    }
}
