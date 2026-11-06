package com.tecsup.ticketservice.dto;

import com.tecsup.ticketservice.model.Estado;
import com.tecsup.ticketservice.model.Prioridad;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/** DTO de SALIDA del ticket. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {

    private Long id;
    private Long idUsuario;
    private Long idTecnico;
    private String asunto;
    private String descripcion;
    private Prioridad prioridad;
    private Estado estado;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaActualizacion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCierre;
}
