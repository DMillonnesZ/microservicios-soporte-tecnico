package com.tecsup.tecnicoservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Regla de negocio violada. Lleva su propio codigo HTTP (400, 409, ...).
 */
@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus status;

    public BusinessException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
