package com.tecsup.usuarioservice.services;

public interface EmailService {

    void enviarCodigoVerificacion(String destinatario, String nombre, String codigo);
}
