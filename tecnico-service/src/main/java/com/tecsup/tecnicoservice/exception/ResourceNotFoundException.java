package com.tecsup.tecnicoservice.exception;

/** Se lanza cuando se busca un ID que no existe -> HTTP 404 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
