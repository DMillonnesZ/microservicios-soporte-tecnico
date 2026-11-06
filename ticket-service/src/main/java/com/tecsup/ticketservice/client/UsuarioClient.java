package com.tecsup.ticketservice.client;

import com.tecsup.ticketservice.dto.UsuarioDTO;
import com.tecsup.ticketservice.exception.ResourceNotFoundException;
import com.tecsup.ticketservice.exception.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente HTTP hacia usuario-service (puerto 8081).
 * Traduce las respuestas del otro servicio a excepciones de este servicio:
 *   404 del otro servicio  -> ResourceNotFoundException (404)
 *   servicio apagado/error -> ServiceUnavailableException (503)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioClient {

    private final RestTemplate restTemplate;

    @Value("${services.usuario.url}")
    private String baseUrl;

    public UsuarioDTO obtenerUsuario(Long id) {
        try {
            return restTemplate.getForObject(baseUrl + "/api/usuarios/{id}", UsuarioDTO.class, id);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Usuario con id " + id + " no existe");
        } catch (RestClientException ex) {
            log.error("Error al consultar usuario-service", ex);
            throw new ServiceUnavailableException("usuario-service no esta disponible en " + baseUrl);
        }
    }
}
