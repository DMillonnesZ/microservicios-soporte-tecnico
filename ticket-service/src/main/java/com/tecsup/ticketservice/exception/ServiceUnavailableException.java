package com.tecsup.ticketservice.exception;

/** Otro microservicio no responde (apagado, puerto incorrecto, timeout) -> HTTP 503 */
public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String message) {
        super(message);
    }
}
