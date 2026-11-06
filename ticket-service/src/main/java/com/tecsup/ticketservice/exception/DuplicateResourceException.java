package com.tecsup.ticketservice.exception;

/** Se lanza cuando se intenta registrar un dato unico repetido (ej. email) -> HTTP 409 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
