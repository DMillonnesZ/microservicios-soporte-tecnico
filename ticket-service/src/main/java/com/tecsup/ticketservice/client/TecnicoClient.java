package com.tecsup.ticketservice.client;

import com.tecsup.ticketservice.dto.TecnicoDTO;
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
 * Cliente HTTP hacia tecnico-service (puerto 8082).
 * Traduce las respuestas del otro servicio a excepciones de este servicio:
 *   404 del otro servicio  -> ResourceNotFoundException (404)
 *   servicio apagado/error -> ServiceUnavailableException (503)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TecnicoClient {

    private final RestTemplate restTemplate;

    @Value("${services.tecnico.url}")
    private String baseUrl;

    public TecnicoDTO obtenerTecnico(Long id) {
        try {
            return restTemplate.getForObject(baseUrl + "/api/tecnicos/{id}", TecnicoDTO.class, id);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Tecnico con id " + id + " no existe");
        } catch (RestClientException ex) {
            log.error("Error al consultar tecnico-service", ex);
            throw new ServiceUnavailableException("tecnico-service no esta disponible en " + baseUrl);
        }
    }
}
