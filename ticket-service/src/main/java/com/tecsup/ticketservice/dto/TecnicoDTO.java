package com.tecsup.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Copia minima de lo que responde tecnico-service (solo los campos que usamos). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TecnicoDTO {

    private Long id;
    private String nombre;
    private String email;
    private String especialidad;
}
