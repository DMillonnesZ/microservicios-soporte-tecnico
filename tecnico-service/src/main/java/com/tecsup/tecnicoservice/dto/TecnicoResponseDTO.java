package com.tecsup.tecnicoservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de SALIDA: lo que la API devuelve al cliente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TecnicoResponseDTO {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String especialidad;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaRegistro;
}
