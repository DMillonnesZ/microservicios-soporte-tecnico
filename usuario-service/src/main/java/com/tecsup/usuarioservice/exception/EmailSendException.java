package com.tecsup.usuarioservice.exception;

/** No se pudo enviar el correo (SMTP caido, credenciales mal, sin internet) -> HTTP 503 */
public class EmailSendException extends RuntimeException {

    public EmailSendException(String message) {
        super(message);
    }
}
